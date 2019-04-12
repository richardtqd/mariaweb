/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import pe.marista.sigma.bean.ConGeneralBean;
import pe.marista.sigma.bean.HistoricoBean;
import pe.marista.sigma.bean.SatisfaccionGrlBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ResDimensionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import pe.marista.sigma.bean.CantidadBean;
import pe.marista.sigma.bean.GrPieChartBean;
import pe.marista.sigma.bean.PoblacionCons;
import pe.marista.sigma.bean.PoblacionGrOcuBean;
import pe.marista.sigma.bean.ResDimensionBean;
import pe.marista.sigma.bean.SugerenciasBean;
/**
 *
 * @author MS001
 */
public class ConGeneralMB implements Serializable {
    
    private List<ConGeneralBean> listaGeneralUninegA,listaGeneralUninegB,listaGeneralUninegC,listaGeneralUninegD,listaGeneralUninegE,listaGeneralUninegF,listaGeneralUninegG,listaGeneralUninegH,listaGeneralUninegI,listaGeneralUninegJ,listaGeneralUninegK,listaGeneralUninegL;
    private List<ConGeneralBean> listaGrpGeneralUninegA,listaGrpGeneralUninegB,listaGrpGeneralUninegC,listaGrpGeneralUninegD,listaGrpGeneralUninegE,listaGrpGeneralUninegF,listaGrpGeneralUninegG,listaGrpGeneralUninegH,listaGrpGeneralUninegI,listaGrpGeneralUninegJ,listaGrpGeneralUninegK,listaGrpGeneralUninegL;
    private BarChartModel barGeneralUninegA,barGeneralUninegB,barGeneralUninegC,barGeneralUninegD,barGeneralUninegE,barGeneralUninegF,barGeneralUninegG,barGeneralUninegH,barGeneralUninegI,barGeneralUninegJ,barGeneralUninegK,barGeneralUninegL;
    private BarChartModel barGrpGeneralUninegA,barGrpGeneralUninegB,barGrpGeneralUninegC,barGrpGeneralUninegD,barGrpGeneralUninegE,barGrpGeneralUninegF,barGrpGeneralUninegG,barGrpGeneralUninegH,barGrpGeneralUninegI,barGrpGeneralUninegJ,barGrpGeneralUninegK,barGrpGeneralUninegL;

    private List<ConGeneralBean> listaGeneralUninegA_est_part,listaGeneralUninegB_est_part,listaGeneralUninegC_est_part,listaGeneralUninegD_est_part,listaGeneralUninegE_est_part,listaGeneralUninegF_est_part,listaGeneralUninegG_est_part,listaGeneralUninegH_est_part,listaGeneralUninegI_est_part,listaGeneralUninegJ_est_part,listaGeneralUninegK_est_part,listaGeneralUninegL_est_part;
    private List<ConGeneralBean> listaGrpGeneralUninegA_est_part,listaGrpGeneralUninegB_est_part,listaGrpGeneralUninegC_est_part,listaGrpGeneralUninegD_est_part,listaGrpGeneralUninegE_est_part,listaGrpGeneralUninegF_est_part,listaGrpGeneralUninegG_est_part,listaGrpGeneralUninegH_est_part,listaGrpGeneralUninegI_est_part,listaGrpGeneralUninegJ_est_part,listaGrpGeneralUninegK_est_part,listaGrpGeneralUninegL_est_part;    
    private BarChartModel barGeneralUninegA_est_part,barGeneralUninegB_est_part,barGeneralUninegC_est_part,barGeneralUninegD_est_part,barGeneralUninegE_est_part,barGeneralUninegF_est_part,barGeneralUninegG_est_part,barGeneralUninegH_est_part,barGeneralUninegI_est_part,barGeneralUninegJ_est_part,barGeneralUninegK_est_part,barGeneralUninegL_est_part;
    private BarChartModel barGrpGeneralUninegA_est_part,barGrpGeneralUninegB_est_part,barGrpGeneralUninegC_est_part,barGrpGeneralUninegD_est_part,barGrpGeneralUninegE_est_part,barGrpGeneralUninegF_est_part,barGrpGeneralUninegG_est_part,barGrpGeneralUninegH_est_part,barGrpGeneralUninegI_est_part,barGrpGeneralUninegJ_est_part,barGrpGeneralUninegK_est_part,barGrpGeneralUninegL_est_part;
    
    private BarChartModel barSatisfaccionGral,barSatisfaccionGralHis,barSatisfaccionGral_est_part;
    private List<SatisfaccionGrlBean> listaSatisfaccionGral,listaSatisfaccionGralHis,listaSatisfaccionGral_est_part;
    private BarChartModel barSatHisSECTOR,barSatHisCRISTC,barSatHisSANJOS,barSatHisSTAMAR,barSatHisUMCH,barSatHisSANLUI,barSatHisBARINA,barSatHisCHAMPC,barSatHisCHAMPS,barSatHisSTAROS,barSatHisSANJOC,barSatHisSANJOH;
    private List<HistoricoBean> listaSatHisSECTOR,listaSatHisSANJOS,listaSatHisCRISTC,listaSatHisSTAMAR,listaSatHisUMCH,listaSatHisSANLUI,listaSatHisBARINA,listaSatHisCHAMPC,listaSatHisCHAMPS,listaSatHisSTAROS,listaSatHisSANJOC,listaSatHisSANJOH;
    private BarChartModel barResDimensionSecPeru,barResDimensionSecPeru_est_part;
    private List<ResDimensionBean> listaResDimensionSecPerubar,listaResDimensionSecPerubar_est_part;
    private List<SugerenciasBean> listaSugerencias;        
    private PieChartModel pieSatisfaccionGeneral;
    private List<GrPieChartBean> listaSatisfaccionGeneral;    
    
    private List<CantidadBean> listaCantConGeneral,listaCantConGeneral_est_part;     
    private List<PoblacionCons> listaCantConsUnineg,listaCantConsUnineg_est_part;    
    private List<PoblacionCons> listaCantConsDimen_A,listaCantConsDimen_B,listaCantConsDimen_C,listaCantConsDimen_D,listaCantConsDimen_E,listaCantConsDimen_F,
    listaCantConsDimen_G,listaCantConsDimen_H,listaCantConsDimen_I,listaCantConsDimen_J,listaCantConsDimen_K,listaCantConsDimen_L;
    private List<PoblacionGrOcuBean> listaCantConsGrpOcu_A,listaCantConsGrpOcu_B,listaCantConsGrpOcu_C,listaCantConsGrpOcu_D,listaCantConsGrpOcu_E,listaCantConsGrpOcu_F,
    listaCantConsGrpOcu_G,listaCantConsGrpOcu_H,listaCantConsGrpOcu_I,listaCantConsGrpOcu_J,listaCantConsGrpOcu_K,listaCantConsGrpOcu_L;     
    
    private List<PoblacionCons> listaCantConsDimen_A_est_part,listaCantConsDimen_B_est_part,listaCantConsDimen_C_est_part,listaCantConsDimen_D_est_part,listaCantConsDimen_E_est_part,listaCantConsDimen_F_est_part,
    listaCantConsDimen_G_est_part,listaCantConsDimen_H_est_part,listaCantConsDimen_I_est_part,listaCantConsDimen_J_est_part,listaCantConsDimen_K_est_part,listaCantConsDimen_L_est_part;
    private List<PoblacionGrOcuBean> listaCantConsGrpOcu_A_est_part,listaCantConsGrpOcu_B_est_part,listaCantConsGrpOcu_C_est_part,listaCantConsGrpOcu_D_est_part,listaCantConsGrpOcu_E_est_part,listaCantConsGrpOcu_F_est_part,
    listaCantConsGrpOcu_G_est_part,listaCantConsGrpOcu_H_est_part,listaCantConsGrpOcu_I_est_part,listaCantConsGrpOcu_J_est_part,listaCantConsGrpOcu_K_est_part,listaCantConsGrpOcu_L_est_part;     
    
    private Integer cant_Consolidado,cant_Consolidado_est_part;
    private List<CantidadBean> cargartablas;
    Calendar fecha = new GregorianCalendar();
    private int  anio = fecha.get(Calendar.YEAR);
    private String tip_colegio="";
    
    @PostConstruct
    public void ConGeneralMB() {
        try {
           ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
           cargartablas= resDimensionService.sp_mc_cargar_tbl_poblaciones();            
           cargarGraficosCons();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void cargarGraficos_est_part(){
        try{        
            anio = fecha.get(Calendar.YEAR);
            tip_colegio="ESTATAL";
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaCantConsUnineg_est_part= resDimensionService.sp_mc_cant_poblacion_cons_unineg_estatal_particular(tip_colegio,anio);
            obtenerSatisfaccionGrlBar_est_part(tip_colegio);
            obtenerResDimensionSecPeruBar_est_part(tip_colegio);
            listaCantConGeneral_est_part= resDimensionService.sp_mc_cant_poblacion_consolidado_estatal_particular(tip_colegio,anio);
            cant_Consolidado_est_part = listaCantConGeneral_est_part.get(0).getCantidad();                  
            obtenerGrpGeneralUniNegBarA_est_part(tip_colegio);
            obtenersatGeneralUniNegBarA_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarB_est_part(tip_colegio);
            obtenersatGeneralUniNegBarB_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarC_est_part(tip_colegio);
            obtenersatGeneralUniNegBarC_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarD_est_part(tip_colegio);
            obtenersatGeneralUniNegBarD_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarE_est_part(tip_colegio);
            obtenersatGeneralUniNegBarE_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarF_est_part(tip_colegio);
            obtenersatGeneralUniNegBarF_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarG_est_part(tip_colegio);
            obtenersatGeneralUniNegBarG_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarH_est_part(tip_colegio);
            obtenersatGeneralUniNegBarH_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarI_est_part(tip_colegio);
            obtenersatGeneralUniNegBarI_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarJ_est_part(tip_colegio);
            obtenersatGeneralUniNegBarJ_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarK_est_part(tip_colegio);
            obtenersatGeneralUniNegBarK_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarL_est_part(tip_colegio);
            obtenersatGeneralUniNegBarL_est_part(tip_colegio);         
            
            listaCantConsDimen_A_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"A",anio);
            listaCantConsGrpOcu_A_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"A",anio);            
            listaCantConsDimen_B_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"B",anio);
            listaCantConsGrpOcu_B_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"B",anio);            
            listaCantConsDimen_C_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"C",anio);
            listaCantConsGrpOcu_C_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"C",anio);            
            listaCantConsDimen_D_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"D",anio);
            listaCantConsGrpOcu_D_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"D",anio);            
            listaCantConsDimen_E_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"E",anio);
            listaCantConsGrpOcu_E_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"E",anio);            
            listaCantConsDimen_F_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"F",anio);
            listaCantConsGrpOcu_F_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"F",anio);            
            listaCantConsDimen_G_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"G",anio);
            listaCantConsGrpOcu_G_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"G",anio);            
            listaCantConsDimen_H_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"H",anio);
            listaCantConsGrpOcu_H_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"H",anio);            
            listaCantConsDimen_I_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"I",anio);
            listaCantConsGrpOcu_I_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"I",anio);            
            listaCantConsDimen_J_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"J",anio);
            listaCantConsGrpOcu_J_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"J",anio);            
            listaCantConsDimen_K_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"K",anio);
            listaCantConsGrpOcu_K_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"K",anio);            
            listaCantConsDimen_L_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"L",anio);
            listaCantConsGrpOcu_L_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"L",anio);                        
            
            
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }    
    }
    public void cargarGraficos_est_particular(){
        
        try{        
            anio = fecha.get(Calendar.YEAR);
            tip_colegio="PARTICULAR";
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaCantConsUnineg_est_part= resDimensionService.sp_mc_cant_poblacion_cons_unineg_estatal_particular(tip_colegio,anio);
            obtenerSatisfaccionGrlBar_est_part(tip_colegio);
            obtenerResDimensionSecPeruBar_est_part(tip_colegio);
            listaCantConGeneral_est_part= resDimensionService.sp_mc_cant_poblacion_consolidado_estatal_particular(tip_colegio,anio);
            cant_Consolidado_est_part = listaCantConGeneral_est_part.get(0).getCantidad();                  
            obtenerGrpGeneralUniNegBarA_est_part(tip_colegio);
            obtenersatGeneralUniNegBarA_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarB_est_part(tip_colegio);
            obtenersatGeneralUniNegBarB_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarC_est_part(tip_colegio);
            obtenersatGeneralUniNegBarC_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarD_est_part(tip_colegio);
            obtenersatGeneralUniNegBarD_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarE_est_part(tip_colegio);
            obtenersatGeneralUniNegBarE_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarF_est_part(tip_colegio);
            obtenersatGeneralUniNegBarF_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarG_est_part(tip_colegio);
            obtenersatGeneralUniNegBarG_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarH_est_part(tip_colegio);
            obtenersatGeneralUniNegBarH_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarI_est_part(tip_colegio);
            obtenersatGeneralUniNegBarI_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarJ_est_part(tip_colegio);
            obtenersatGeneralUniNegBarJ_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarK_est_part(tip_colegio);
            obtenersatGeneralUniNegBarK_est_part(tip_colegio);
            obtenerGrpGeneralUniNegBarL_est_part(tip_colegio);
            obtenersatGeneralUniNegBarL_est_part(tip_colegio);         
            
            listaCantConsDimen_A_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"A",anio);
            listaCantConsGrpOcu_A_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"A",anio);            
            listaCantConsDimen_B_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"B",anio);
            listaCantConsGrpOcu_B_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"B",anio);            
            listaCantConsDimen_C_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"C",anio);
            listaCantConsGrpOcu_C_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"C",anio);            
            listaCantConsDimen_D_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"D",anio);
            listaCantConsGrpOcu_D_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"D",anio);            
            listaCantConsDimen_E_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"E",anio);
            listaCantConsGrpOcu_E_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"E",anio);            
            listaCantConsDimen_F_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"F",anio);
            listaCantConsGrpOcu_F_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"F",anio);            
            listaCantConsDimen_G_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"G",anio);
            listaCantConsGrpOcu_G_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"G",anio);            
            listaCantConsDimen_H_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"H",anio);
            listaCantConsGrpOcu_H_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"H",anio);            
            listaCantConsDimen_I_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"I",anio);
            listaCantConsGrpOcu_I_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"I",anio);            
            listaCantConsDimen_J_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"J",anio);
            listaCantConsGrpOcu_J_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"J",anio);            
            listaCantConsDimen_K_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"K",anio);
            listaCantConsGrpOcu_K_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"K",anio);            
            listaCantConsDimen_L_est_part= resDimensionService.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio,"L",anio);
            listaCantConsGrpOcu_L_est_part = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio,"L",anio);                        
            
            
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }       
 
    }
    public void cargarGraficosCons(){
        
        try{
            
            obtenersatGeneralUniNegBar();
            obtenerGrpGeneralUniNegBar();
            obtenersatGeneralUniNegBarB();
            obtenerGrpGeneralUniNegBarB();
            obtenersatGeneralUniNegBarC();
            obtenerGrpGeneralUniNegBarC();
            obtenersatGeneralUniNegBarD();
            obtenerGrpGeneralUniNegBarD();
            obtenersatGeneralUniNegBarE();
            obtenerGrpGeneralUniNegBarE();
            obtenersatGeneralUniNegBarF();
            obtenerGrpGeneralUniNegBarF();
            obtenersatGeneralUniNegBarG();
            obtenerGrpGeneralUniNegBarG();
            obtenersatGeneralUniNegBarH();
            obtenerGrpGeneralUniNegBarH();
            obtenersatGeneralUniNegBarI();
            obtenerGrpGeneralUniNegBarI();
            obtenersatGeneralUniNegBarJ();
            obtenerGrpGeneralUniNegBarJ();
            obtenersatGeneralUniNegBarK();
            obtenerGrpGeneralUniNegBarK();
            obtenersatGeneralUniNegBarL();
            obtenerGrpGeneralUniNegBarL();             
            obtenerSatisfaccionGrlBar();
            obtenerSatHistoricoBarSANLUI();
            obtenerSatHistoricoBarBARINA();
            obtenerSatHistoricoBarSANJOH();
            obtenerSatHistoricoBarSANJOC();
            obtenerSatHistoricoBarSTAROS();
            obtenerSatHistoricoBarCHAMPS();
            obtenerSatHistoricoBarCHAMPC(); 
            obtenerSatHistoricoBarSTAMAR();
            obtenerSatHistoricoBarUMCH();
            obtenerSatHistoricoBarCRISTC();
            obtenerSatHistoricoBarSANJOS();
            obtenerSatHistoricoBarSECTOR();
            obtenerResDimensionSecPeruBar();
            obtenerSatisfaccionConGeneralPie();

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSugerencias = resDimensionService.sp_mc_sugerencias(anio);

            listaCantConGeneral= resDimensionService.sp_mc_cant_poblacion_consolidado(anio);
            cant_Consolidado = listaCantConGeneral.get(0).getCantidad();

            listaCantConsUnineg= resDimensionService.sp_mc_cant_poblacion_cons_unineg(anio);
            System.out.println(" CANTIDAD DIMENSION CONSOLIDAD ====> " + anio);
            listaCantConsDimen_A= resDimensionService.sp_mc_cant_poblacion_cons_dim("A",anio);
            listaCantConsGrpOcu_A = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("A",anio);
            listaCantConsDimen_B= resDimensionService.sp_mc_cant_poblacion_cons_dim("B",anio);
            listaCantConsGrpOcu_B = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("B",anio);
            listaCantConsDimen_C= resDimensionService.sp_mc_cant_poblacion_cons_dim("C",anio);
            listaCantConsGrpOcu_C = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("C",anio);
            listaCantConsDimen_D= resDimensionService.sp_mc_cant_poblacion_cons_dim("D",anio);
            listaCantConsGrpOcu_D = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("D",anio);
            listaCantConsDimen_E= resDimensionService.sp_mc_cant_poblacion_cons_dim("E",anio);
            listaCantConsGrpOcu_E = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("E",anio);
            listaCantConsDimen_F= resDimensionService.sp_mc_cant_poblacion_cons_dim("F",anio);
            listaCantConsGrpOcu_F = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("F",anio);
            listaCantConsDimen_G= resDimensionService.sp_mc_cant_poblacion_cons_dim("G",anio);
            listaCantConsGrpOcu_G = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("G",anio);
            listaCantConsDimen_H= resDimensionService.sp_mc_cant_poblacion_cons_dim("H",anio);
            listaCantConsGrpOcu_H = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("H",anio);
            listaCantConsDimen_I= resDimensionService.sp_mc_cant_poblacion_cons_dim("I",anio);
            listaCantConsGrpOcu_I = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("I",anio);            
            listaCantConsDimen_J= resDimensionService.sp_mc_cant_poblacion_cons_dim("J",anio);
            listaCantConsGrpOcu_J = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("J",anio);
            listaCantConsDimen_K= resDimensionService.sp_mc_cant_poblacion_cons_dim("K",anio);
            listaCantConsGrpOcu_K = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("K",anio);
            listaCantConsDimen_L= resDimensionService.sp_mc_cant_poblacion_cons_dim("L",anio);
            listaCantConsGrpOcu_L = resDimensionService.sp_mc_cant_poblacion_cons_grupoocu("L",anio);
        //          execProUpdate();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

 /********************************* DIMENSION  A   ESTATAL - PARTICULAR *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarA_est_part(String tip_colegio) {
        barGeneralUninegA_est_part = new BarChartModel();
        
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegA_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"A",anio);            
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegA_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegA_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarA : listaGeneralUninegA_est_part) {
                    res = Math.round(resGeneralbarA.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarA.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarA.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarA.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarA.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarA.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarA.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarA.getSatisfecho().floatValue())));    
                        resGeneralbarA.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarA.getNombre(), resGeneralbarA.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarA.getNombre(), resGeneralbarA.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarA.getNombre(), resGeneralbarA.getSatisfecho().floatValue());
                }
                barGeneralUninegA_est_part.setTitle("Infraestructura y ambiente de trabajo");
                barGeneralUninegA_est_part.setLegendPosition("ne");
                barGeneralUninegA_est_part.setShowPointLabels(true);
                barGeneralUninegA_est_part.setAnimate(true);
                barGeneralUninegA_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegA_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegA_est_part.addSeries(satisfecho);
        barGeneralUninegA_est_part.addSeries(med_satisfecho);
        barGeneralUninegA_est_part.addSeries(no_satisfecho);
        return barGeneralUninegA_est_part;
    }       

  /*** DIMENSION   A   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarA_est_part(String tip_colegio) {
        barGrpGeneralUninegA_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegA_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"A",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegA_est_part.size());
            if (!listaGrpGeneralUninegA_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarA : listaGrpGeneralUninegA_est_part) {
                    res = Math.round(resGrpGeneralbarA.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarA.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarA.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarA.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarA.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarA.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarA.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarA.getSatisfecho().floatValue())));    
                        resGrpGeneralbarA.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarA.getNombre(), resGrpGeneralbarA.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarA.getNombre(), resGrpGeneralbarA.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarA.getNombre(), resGrpGeneralbarA.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegA_est_part.setTitle("Infraestructura y ambiente de trabajo por grupos ocupacionales");
                barGrpGeneralUninegA_est_part.setLegendPosition("ne");
                barGrpGeneralUninegA_est_part.setShowPointLabels(true);
                barGrpGeneralUninegA_est_part.setAnimate(true);
                barGrpGeneralUninegA_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegA_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegA_est_part.addSeries(satisfecho);
        barGrpGeneralUninegA_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegA_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegA_est_part;
    }         
    
/********************************* DIMENSION  B *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarB_est_part(String tip_colegio) {
        barGeneralUninegB_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegB_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"B",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegB.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegB_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarB : listaGeneralUninegB_est_part) {
                    res = Math.round(resGeneralbarB.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarB.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarB.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarB.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarB.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarB.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarB.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarB.getSatisfecho().floatValue())));    
                        resGeneralbarB.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarB.getNombre(), resGeneralbarB.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarB.getNombre(), resGeneralbarB.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarB.getNombre(), resGeneralbarB.getSatisfecho().floatValue());
                }
                barGeneralUninegB_est_part.setTitle("Organización, roles y funciones");
                barGeneralUninegB_est_part.setLegendPosition("ne");
                barGeneralUninegB_est_part.setShowPointLabels(true);
                barGeneralUninegB_est_part.setAnimate(true);
                barGeneralUninegB_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegB_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegB_est_part.addSeries(satisfecho);
        barGeneralUninegB_est_part.addSeries(med_satisfecho);
        barGeneralUninegB_est_part.addSeries(no_satisfecho);
        return barGeneralUninegB_est_part;
    }       

  /*** DIMENSION   B   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarB_est_part(String tip_colegio) {
        barGrpGeneralUninegB_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegB_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"B",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegB_est_part.size());
            if (!listaGrpGeneralUninegB_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarB : listaGrpGeneralUninegB_est_part) {
                    res = Math.round(resGrpGeneralbarB.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarB.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarB.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarB.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarB.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarB.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarB.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarB.getSatisfecho().floatValue())));    
                        resGrpGeneralbarB.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarB.getNombre(), resGrpGeneralbarB.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarB.getNombre(), resGrpGeneralbarB.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarB.getNombre(), resGrpGeneralbarB.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegB_est_part.setTitle("Organización, roles y funciones por grupos ocupacionales");
                barGrpGeneralUninegB_est_part.setLegendPosition("ne");
                barGrpGeneralUninegB_est_part.setShowPointLabels(true);
                barGrpGeneralUninegB_est_part.setAnimate(true);
                barGrpGeneralUninegB_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegB_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegB_est_part.addSeries(satisfecho);
        barGrpGeneralUninegB_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegB_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegB_est_part;
    }       
    
 /********************************* DIMENSION  C *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarC_est_part(String tip_colegio) {
        barGeneralUninegC_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegC_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"C",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegC_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegC_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarC : listaGeneralUninegC_est_part) {
                    res = Math.round(resGeneralbarC.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarC.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarC.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarC.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarC.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarC.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarC.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarC.getSatisfecho().floatValue())));    
                        resGeneralbarC.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarC.getNombre(), resGeneralbarC.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarC.getNombre(), resGeneralbarC.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarC.getNombre(), resGeneralbarC.getSatisfecho().floatValue());
                }
                barGeneralUninegC_est_part.setTitle("Trabajo en equipo");
                barGeneralUninegC_est_part.setLegendPosition("ne");
                barGeneralUninegC_est_part.setShowPointLabels(true);
                barGeneralUninegC_est_part.setAnimate(true);
                barGeneralUninegC_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegC_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegC_est_part.addSeries(satisfecho);
        barGeneralUninegC_est_part.addSeries(med_satisfecho);
        barGeneralUninegC_est_part.addSeries(no_satisfecho);
        return barGeneralUninegC_est_part;
    }       

  /*** DIMENSION   C   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarC_est_part(String tip_colegio) {
        barGrpGeneralUninegC_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegC_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"C",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegC_est_part.size());
            if (!listaGrpGeneralUninegC_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarC : listaGrpGeneralUninegC_est_part) {
                    res = Math.round(resGrpGeneralbarC.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarC.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarC.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarC.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarC.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarC.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarC.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarC.getSatisfecho().floatValue())));    
                        resGrpGeneralbarC.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarC.getNombre(), resGrpGeneralbarC.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarC.getNombre(), resGrpGeneralbarC.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarC.getNombre(), resGrpGeneralbarC.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegC_est_part.setTitle("Trabajo en equipo por grupos ocupacionales");
                barGrpGeneralUninegC_est_part.setLegendPosition("ne");
                barGrpGeneralUninegC_est_part.setShowPointLabels(true);
                barGrpGeneralUninegC_est_part.setAnimate(true);
                barGrpGeneralUninegC_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegC_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegC_est_part.addSeries(satisfecho);
        barGrpGeneralUninegC_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegC_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegC_est_part;
    }      
    
     /********************************* DIMENSION  D *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarD_est_part(String tip_colegio) {
        barGeneralUninegD_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegD_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"D",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegD_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegD_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarD : listaGeneralUninegD_est_part) {
                    res = Math.round(resGeneralbarD.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarD.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarD.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarD.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarD.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarD.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarD.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarD.getSatisfecho().floatValue())));    
                        resGeneralbarD.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarD.getNombre(), resGeneralbarD.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarD.getNombre(), resGeneralbarD.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarD.getNombre(), resGeneralbarD.getSatisfecho().floatValue());
                }
                barGeneralUninegD_est_part.setTitle("Relaciones interpersonales");
                barGeneralUninegD_est_part.setLegendPosition("ne");
                barGeneralUninegD_est_part.setShowPointLabels(true);
                barGeneralUninegD_est_part.setAnimate(true);
                barGeneralUninegD_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegD_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegD_est_part.addSeries(satisfecho);
        barGeneralUninegD_est_part.addSeries(med_satisfecho);
        barGeneralUninegD_est_part.addSeries(no_satisfecho);
        return barGeneralUninegD_est_part;
    }       

  /*** DIMENSION   D   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarD_est_part(String tip_colegio) {
        barGrpGeneralUninegD_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegD_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"D",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegD_est_part.size());
            if (!listaGrpGeneralUninegD_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarD : listaGrpGeneralUninegD_est_part) {
                    res = Math.round(resGrpGeneralbarD.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarD.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarD.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarD.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarD.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarD.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarD.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarD.getSatisfecho().floatValue())));    
                        resGrpGeneralbarD.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarD.getNombre(), resGrpGeneralbarD.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarD.getNombre(), resGrpGeneralbarD.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarD.getNombre(), resGrpGeneralbarD.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegD_est_part.setTitle("Relaciones interpersonales por grupos ocupacionales");
                barGrpGeneralUninegD_est_part.setLegendPosition("ne");
                barGrpGeneralUninegD_est_part.setShowPointLabels(true);
                barGrpGeneralUninegD_est_part.setAnimate(true);
                barGrpGeneralUninegD_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegD_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegD_est_part.addSeries(satisfecho);
        barGrpGeneralUninegD_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegD_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegD_est_part;
    }  
  /********************************* DIMENSION  E *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarE_est_part(String tip_colegio) {
        barGeneralUninegE_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegE_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"E",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegE.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegE_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarE : listaGeneralUninegE_est_part) {
                    res = Math.round(resGeneralbarE.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarE.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarE.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarE.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarE.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarE.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarE.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarE.getSatisfecho().floatValue())));    
                        resGeneralbarE.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarE.getNombre(), resGeneralbarE.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarE.getNombre(), resGeneralbarE.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarE.getNombre(), resGeneralbarE.getSatisfecho().floatValue());
                }
                barGeneralUninegE_est_part.setTitle("Manejo de conflictos");
                barGeneralUninegE_est_part.setLegendPosition("ne");
                barGeneralUninegE_est_part.setShowPointLabels(true);
                barGeneralUninegE_est_part.setAnimate(true);
                barGeneralUninegE_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegE_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegE_est_part.addSeries(satisfecho);
        barGeneralUninegE_est_part.addSeries(med_satisfecho);
        barGeneralUninegE_est_part.addSeries(no_satisfecho);
        return barGeneralUninegE_est_part;
    }       

  /*** DIMENSION   E   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarE_est_part(String tip_colegio) {
        barGrpGeneralUninegE_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
		
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegE_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"E",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegE_est_part.size());
            if (!listaGrpGeneralUninegE_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarE : listaGrpGeneralUninegE_est_part) {
                    res = Math.round(resGrpGeneralbarE.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarE.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarE.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarE.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarE.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarE.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarE.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarE.getSatisfecho().floatValue())));    
                        resGrpGeneralbarE.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarE.getNombre(), resGrpGeneralbarE.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarE.getNombre(), resGrpGeneralbarE.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarE.getNombre(), resGrpGeneralbarE.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegE_est_part.setTitle("Manejo de conflictos por grupos ocupacionales");
                barGrpGeneralUninegE_est_part.setLegendPosition("ne");
                barGrpGeneralUninegE_est_part.setShowPointLabels(true);
                barGrpGeneralUninegE_est_part.setAnimate(true);
                barGrpGeneralUninegE_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegE_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegE_est_part.addSeries(satisfecho);
        barGrpGeneralUninegE_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegE_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegE_est_part;
    }     
    
 /********************************* DIMENSION  F *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarF_est_part(String tip_colegio) {
        barGeneralUninegF_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
		
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegF_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"F",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegF_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegF_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarF : listaGeneralUninegF_est_part) {
                     res = Math.round(resGeneralbarF.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarF.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarF.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarF.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarF.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarF.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarF.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarF.getSatisfecho().floatValue())));    
                        resGeneralbarF.setMedSatisfecho(newval);
                    }                   
                    no_satisfecho.set(resGeneralbarF.getNombre(), resGeneralbarF.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarF.getNombre(), resGeneralbarF.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarF.getNombre(), resGeneralbarF.getSatisfecho().floatValue());
                }
                barGeneralUninegF_est_part.setTitle("Orgullo y Compromiso");
                barGeneralUninegF_est_part.setLegendPosition("ne");
                barGeneralUninegF_est_part.setShowPointLabels(true);
                barGeneralUninegF_est_part.setAnimate(true);
                barGeneralUninegF_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegF_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegF_est_part.addSeries(satisfecho);
        barGeneralUninegF_est_part.addSeries(med_satisfecho);
        barGeneralUninegF_est_part.addSeries(no_satisfecho);
        return barGeneralUninegF_est_part;
    }       

  /*** DIMENSION   F   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarF_est_part(String tip_colegio) {
        barGrpGeneralUninegF_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegF_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"F",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegF_est_part.size());
            if (!listaGrpGeneralUninegF_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarF : listaGrpGeneralUninegF_est_part) {
                    res = Math.round(resGrpGeneralbarF.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarF.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarF.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarF.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarF.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarF.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarF.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarF.getSatisfecho().floatValue())));    
                        resGrpGeneralbarF.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarF.getNombre(), resGrpGeneralbarF.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarF.getNombre(), resGrpGeneralbarF.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarF.getNombre(), resGrpGeneralbarF.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegF_est_part.setTitle("Orgullo y Compromiso por grupos ocupacionales");
                barGrpGeneralUninegF_est_part.setLegendPosition("ne");
                barGrpGeneralUninegF_est_part.setShowPointLabels(true);
                barGrpGeneralUninegF_est_part.setAnimate(true);
                barGrpGeneralUninegF_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegF_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegF_est_part.addSeries(satisfecho);
        barGrpGeneralUninegF_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegF_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegF_est_part;
    }      
    
 /********************************* DIMENSION  G *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarG_est_part(String tip_colegio) {
        barGeneralUninegG_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegG_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"G",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegG_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegG_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarG : listaGeneralUninegG_est_part) {
                    res = Math.round(resGeneralbarG.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarG.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarG.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarG.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarG.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarG.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarG.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarG.getSatisfecho().floatValue())));    
                        resGeneralbarG.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarG.getNombre(), resGeneralbarG.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarG.getNombre(), resGeneralbarG.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarG.getNombre(), resGeneralbarG.getSatisfecho().floatValue());
                }
                barGeneralUninegG_est_part.setTitle("Comunicación");
                barGeneralUninegG_est_part.setLegendPosition("ne");
                barGeneralUninegG_est_part.setShowPointLabels(true);
                barGeneralUninegG_est_part.setAnimate(true);
                barGeneralUninegG_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegG_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegG_est_part.addSeries(satisfecho);
        barGeneralUninegG_est_part.addSeries(med_satisfecho);
        barGeneralUninegG_est_part.addSeries(no_satisfecho);
        return barGeneralUninegG_est_part;
    }       

  /*** DIMENSION   G   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarG_est_part(String tip_colegio) {
        barGrpGeneralUninegG_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegG_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"G",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegG_est_part.size());
            if (!listaGrpGeneralUninegG_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarG : listaGrpGeneralUninegG_est_part) {
                    res = Math.round(resGrpGeneralbarG.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarG.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarG.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarG.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarG.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarG.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarG.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarG.getSatisfecho().floatValue())));    
                        resGrpGeneralbarG.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarG.getNombre(), resGrpGeneralbarG.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarG.getNombre(), resGrpGeneralbarG.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarG.getNombre(), resGrpGeneralbarG.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegG_est_part.setTitle("Comunicación por grupos ocupacionales");
                barGrpGeneralUninegG_est_part.setLegendPosition("ne");
                barGrpGeneralUninegG_est_part.setShowPointLabels(true);
                barGrpGeneralUninegG_est_part.setAnimate(true);
                barGrpGeneralUninegG_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegG_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegG_est_part.addSeries(satisfecho);
        barGrpGeneralUninegG_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegG_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegG_est_part;
    }      
    
 /********************************* DIMENSION  H *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarH_est_part(String tip_colegio) {
        barGeneralUninegH_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegH_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"H",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegH_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegH_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarH : listaGeneralUninegH_est_part) {
                    res = Math.round(resGeneralbarH.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarH.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarH.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarH.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarH.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarH.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarH.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarH.getSatisfecho().floatValue())));    
                        resGeneralbarH.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarH.getNombre(), resGeneralbarH.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarH.getNombre(), resGeneralbarH.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarH.getNombre(), resGeneralbarH.getSatisfecho().floatValue());
                }
                barGeneralUninegH_est_part.setTitle("Reconocimiento y beneficios");
                barGeneralUninegH_est_part.setLegendPosition("ne");
                barGeneralUninegH_est_part.setShowPointLabels(true);
                barGeneralUninegH_est_part.setAnimate(true);
                barGeneralUninegH_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegH_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegH_est_part.addSeries(satisfecho);
        barGeneralUninegH_est_part.addSeries(med_satisfecho);
        barGeneralUninegH_est_part.addSeries(no_satisfecho);
        return barGeneralUninegH_est_part;
    }       

  /*** DIMENSION   H   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarH_est_part(String tip_colegio) {
        barGrpGeneralUninegH_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegH_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"H",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegH_est_part.size());
            if (!listaGrpGeneralUninegH_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarH : listaGrpGeneralUninegH_est_part) {
                    res = Math.round(resGrpGeneralbarH.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarH.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarH.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarH.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarH.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarH.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarH.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarH.getSatisfecho().floatValue())));    
                        resGrpGeneralbarH.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarH.getNombre(), resGrpGeneralbarH.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarH.getNombre(), resGrpGeneralbarH.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarH.getNombre(), resGrpGeneralbarH.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegH_est_part.setTitle("Reconocimiento y beneficios por grupos ocupacionales");
                barGrpGeneralUninegH_est_part.setLegendPosition("ne");
                barGrpGeneralUninegH_est_part.setShowPointLabels(true);
                barGrpGeneralUninegH_est_part.setAnimate(true);
                barGrpGeneralUninegH_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegH_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegH_est_part.addSeries(satisfecho);
        barGrpGeneralUninegH_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegH_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegH_est_part;
    }      
    
 /********************************* DIMENSION  I *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarI_est_part(String tip_colegio) {
        barGeneralUninegI_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegI_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"I",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegI_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegI_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarI : listaGeneralUninegI_est_part) {
                    res = Math.round(resGeneralbarI.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarI.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarI.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarI.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarI.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarI.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarI.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarI.getSatisfecho().floatValue())));    
                        resGeneralbarI.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarI.getNombre(), resGeneralbarI.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarI.getNombre(), resGeneralbarI.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarI.getNombre(), resGeneralbarI.getSatisfecho().floatValue());
                }
                barGeneralUninegI_est_part.setTitle("Capacitación y Desarrollo");
                barGeneralUninegI_est_part.setLegendPosition("ne");
                barGeneralUninegI_est_part.setShowPointLabels(true);
                barGeneralUninegI_est_part.setAnimate(true);
                barGeneralUninegI_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegI_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegI_est_part.addSeries(satisfecho);
        barGeneralUninegI_est_part.addSeries(med_satisfecho);
        barGeneralUninegI_est_part.addSeries(no_satisfecho);
        return barGeneralUninegI_est_part;
    }       

  /*** DIMENSION   I   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarI_est_part(String tip_colegio) {
        barGrpGeneralUninegI_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegI_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"I",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegI_est_part.size());
            if (!listaGrpGeneralUninegI_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarI : listaGrpGeneralUninegI_est_part) {
                    res = Math.round(resGrpGeneralbarI.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarI.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarI.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarI.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarI.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarI.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarI.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarI.getSatisfecho().floatValue())));    
                        resGrpGeneralbarI.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarI.getNombre(), resGrpGeneralbarI.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarI.getNombre(), resGrpGeneralbarI.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarI.getNombre(), resGrpGeneralbarI.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegI_est_part.setTitle("Capacitación y Desarrollo por grupos ocupacionales");
                barGrpGeneralUninegI_est_part.setLegendPosition("ne");
                barGrpGeneralUninegI_est_part.setShowPointLabels(true);
                barGrpGeneralUninegI_est_part.setAnimate(true);
                barGrpGeneralUninegI_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegI_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegI_est_part.addSeries(satisfecho);
        barGrpGeneralUninegI_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegI_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegI_est_part;
    }      
    
 /********************************* DIMENSION  J *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarJ_est_part(String tip_colegio) {
        barGeneralUninegJ_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegJ_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"J",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegJ_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegJ_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarJ : listaGeneralUninegJ_est_part) {
                    res = Math.round(resGeneralbarJ.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarJ.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarJ.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarJ.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarJ.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarJ.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarJ.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarJ.getSatisfecho().floatValue())));    
                        resGeneralbarJ.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarJ.getNombre(), resGeneralbarJ.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarJ.getNombre(), resGeneralbarJ.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarJ.getNombre(), resGeneralbarJ.getSatisfecho().floatValue());
                }
                barGeneralUninegJ_est_part.setTitle("Carga de trabajo y Remuneración");
                barGeneralUninegJ_est_part.setLegendPosition("ne");
                barGeneralUninegJ_est_part.setShowPointLabels(true);
                barGeneralUninegJ_est_part.setAnimate(true);
                barGeneralUninegJ_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegJ_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegJ_est_part.addSeries(satisfecho);
        barGeneralUninegJ_est_part.addSeries(med_satisfecho);
        barGeneralUninegJ_est_part.addSeries(no_satisfecho);
        return barGeneralUninegJ_est_part;
    }       

  /*** DIMENSION   J   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarJ_est_part(String tip_colegio) {
        barGrpGeneralUninegJ_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegJ_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"J",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegJ_est_part.size());
            if (!listaGrpGeneralUninegJ_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarJ : listaGrpGeneralUninegJ_est_part) {
                    res = Math.round(resGrpGeneralbarJ.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarJ.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarJ.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarJ.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarJ.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarJ.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarJ.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarJ.getSatisfecho().floatValue())));    
                        resGrpGeneralbarJ.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarJ.getNombre(), resGrpGeneralbarJ.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarJ.getNombre(), resGrpGeneralbarJ.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarJ.getNombre(), resGrpGeneralbarJ.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegJ_est_part.setTitle("Carga de trabajo y Remuneración por grupos ocupacionales");
                barGrpGeneralUninegJ_est_part.setLegendPosition("ne");
                barGrpGeneralUninegJ_est_part.setShowPointLabels(true);
                barGrpGeneralUninegJ_est_part.setAnimate(true);
                barGrpGeneralUninegJ_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegJ_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegJ_est_part.addSeries(satisfecho);
        barGrpGeneralUninegJ_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegJ_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegJ_est_part;
    }      
    
 /********************************* DIMENSION  K *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarK_est_part(String tip_colegio) {
        barGeneralUninegK_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegK_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"K",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegK_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegK_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarK : listaGeneralUninegK_est_part) {
                    res = Math.round(resGeneralbarK.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarK.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarK.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarK.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarK.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarK.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarK.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarK.getSatisfecho().floatValue())));    
                        resGeneralbarK.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarK.getNombre(), resGeneralbarK.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarK.getNombre(), resGeneralbarK.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarK.getNombre(), resGeneralbarK.getSatisfecho().floatValue());
                }
                barGeneralUninegK_est_part.setTitle("Liderazgo");
                barGeneralUninegK_est_part.setLegendPosition("ne");
                barGeneralUninegK_est_part.setShowPointLabels(true);
                barGeneralUninegK_est_part.setAnimate(true);
                barGeneralUninegK_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegK_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegK_est_part.addSeries(satisfecho);
        barGeneralUninegK_est_part.addSeries(med_satisfecho);
        barGeneralUninegK_est_part.addSeries(no_satisfecho);
        return barGeneralUninegK_est_part;
    }       

  /*** DIMENSION   K   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarK_est_part(String tip_colegio) {
        barGrpGeneralUninegK_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegK_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"K",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegK_est_part.size());
            if (!listaGrpGeneralUninegK_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarK : listaGrpGeneralUninegK_est_part) {
                    res = Math.round(resGrpGeneralbarK.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarK.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarK.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarK.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarK.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarK.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarK.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarK.getSatisfecho().floatValue())));    
                        resGrpGeneralbarK.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarK.getNombre(), resGrpGeneralbarK.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarK.getNombre(), resGrpGeneralbarK.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarK.getNombre(), resGrpGeneralbarK.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegK_est_part.setTitle("Liderazgo por grupos ocupacionales");
                barGrpGeneralUninegK_est_part.setLegendPosition("ne");
                barGrpGeneralUninegK_est_part.setShowPointLabels(true);
                barGrpGeneralUninegK_est_part.setAnimate(true);
                barGrpGeneralUninegK_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegK_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegK_est_part.addSeries(satisfecho);
        barGrpGeneralUninegK_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegK_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegK_est_part;
    }      
    
 /********************************* DIMENSION  L *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarL_est_part(String tip_colegio) {
        barGeneralUninegL_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegL_est_part = resDimensionService.sp_mc_sat_general_unineg_estatal_particular(tip_colegio,"L",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegL_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegL_est_part.isEmpty()) {
                for (ConGeneralBean resGeneralbarL : listaGeneralUninegL_est_part) {
                    res = Math.round(resGeneralbarL.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarL.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarL.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarL.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarL.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarL.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarL.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarL.getSatisfecho().floatValue())));    
                        resGeneralbarL.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarL.getNombre(), resGeneralbarL.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarL.getNombre(), resGeneralbarL.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarL.getNombre(), resGeneralbarL.getSatisfecho().floatValue());
                }
                barGeneralUninegL_est_part.setTitle("Carisma Marista");
                barGeneralUninegL_est_part.setLegendPosition("ne");
                barGeneralUninegL_est_part.setShowPointLabels(true);
                barGeneralUninegL_est_part.setAnimate(true);
                barGeneralUninegL_est_part.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegL_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegL_est_part.addSeries(satisfecho);
        barGeneralUninegL_est_part.addSeries(med_satisfecho);
        barGeneralUninegL_est_part.addSeries(no_satisfecho);
        return barGeneralUninegL_est_part;
    }       

  /*** DIMENSION   L   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarL_est_part(String tip_colegio) {
        barGrpGeneralUninegL_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegL_est_part = resDimensionService.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio,"L",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegL_est_part.size());
            if (!listaGrpGeneralUninegL_est_part.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarL : listaGrpGeneralUninegL_est_part) {
                    res = Math.round(resGrpGeneralbarL.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarL.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarL.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarL.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarL.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarL.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarL.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarL.getSatisfecho().floatValue())));    
                        resGrpGeneralbarL.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarL.getNombre(), resGrpGeneralbarL.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarL.getNombre(), resGrpGeneralbarL.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarL.getNombre(), resGrpGeneralbarL.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegL_est_part.setTitle("Carisma Marista por grupos ocupacionales");
                barGrpGeneralUninegL_est_part.setLegendPosition("ne");
                barGrpGeneralUninegL_est_part.setShowPointLabels(true);
                barGrpGeneralUninegL_est_part.setAnimate(true);
                barGrpGeneralUninegL_est_part.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegL_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegL_est_part.addSeries(satisfecho);
        barGrpGeneralUninegL_est_part.addSeries(med_satisfecho);
        barGrpGeneralUninegL_est_part.addSeries(no_satisfecho);
        return barGrpGeneralUninegL_est_part;
    }    
 /***************************************************************************************************/
 /***************************************************************************************************/
/****************************************************************************************************/
/****************************************************************************************************/
/****************************************************************************************************/    
 /********************************* DIMENSION  A *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBar() {
        barGeneralUninegA = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegA = resDimensionService.sp_mc_sat_general_unineg("A",anio);            
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegA.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegA.isEmpty()) {
                for (ConGeneralBean resGeneralbarA : listaGeneralUninegA) {
                    res = Math.round(resGeneralbarA.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarA.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarA.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarA.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarA.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarA.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarA.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarA.getSatisfecho().floatValue())));    
                        resGeneralbarA.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarA.getNombre(), resGeneralbarA.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarA.getNombre(), resGeneralbarA.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarA.getNombre(), resGeneralbarA.getSatisfecho().floatValue());
                }
                barGeneralUninegA.setTitle("Infraestructura y ambiente de trabajo");
                barGeneralUninegA.setLegendPosition("ne");
                barGeneralUninegA.setShowPointLabels(true);
                barGeneralUninegA.setAnimate(true);
                barGeneralUninegA.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegA.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegA.addSeries(satisfecho);
        barGeneralUninegA.addSeries(med_satisfecho);
        barGeneralUninegA.addSeries(no_satisfecho);
        return barGeneralUninegA;
    }       

  /*** DIMENSION   A   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBar() {
        barGrpGeneralUninegA = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegA = resDimensionService.sp_mc_sat_general_dimensiones("A",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegA.size());
            if (!listaGrpGeneralUninegA.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarA : listaGrpGeneralUninegA) {
                    res = Math.round(resGrpGeneralbarA.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarA.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarA.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarA.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarA.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarA.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarA.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarA.getSatisfecho().floatValue())));    
                        resGrpGeneralbarA.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarA.getNombre(), resGrpGeneralbarA.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarA.getNombre(), resGrpGeneralbarA.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarA.getNombre(), resGrpGeneralbarA.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegA.setTitle("Infraestructura y ambiente de trabajo por grupos ocupacionales");
                barGrpGeneralUninegA.setLegendPosition("ne");
                barGrpGeneralUninegA.setShowPointLabels(true);
                barGrpGeneralUninegA.setAnimate(true);
                barGrpGeneralUninegA.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegA.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegA.addSeries(satisfecho);
        barGrpGeneralUninegA.addSeries(med_satisfecho);
        barGrpGeneralUninegA.addSeries(no_satisfecho);
        return barGrpGeneralUninegA;
    }        
 /********************************* DIMENSION  B *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarB() {
        barGeneralUninegB = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegB = resDimensionService.sp_mc_sat_general_unineg("B",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegB.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegB.isEmpty()) {
                for (ConGeneralBean resGeneralbarB : listaGeneralUninegB) {
                    res = Math.round(resGeneralbarB.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarB.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarB.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarB.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarB.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarB.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarB.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarB.getSatisfecho().floatValue())));    
                        resGeneralbarB.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarB.getNombre(), resGeneralbarB.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarB.getNombre(), resGeneralbarB.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarB.getNombre(), resGeneralbarB.getSatisfecho().floatValue());
                }
                barGeneralUninegB.setTitle("Organización, roles y funciones");
                barGeneralUninegB.setLegendPosition("ne");
                barGeneralUninegB.setShowPointLabels(true);
                barGeneralUninegB.setAnimate(true);
                barGeneralUninegB.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegB.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegB.addSeries(satisfecho);
        barGeneralUninegB.addSeries(med_satisfecho);
        barGeneralUninegB.addSeries(no_satisfecho);
        return barGeneralUninegB;
    }       

  /*** DIMENSION   B   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarB() {
        barGrpGeneralUninegB = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegB = resDimensionService.sp_mc_sat_general_dimensiones("B",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegB.size());
            if (!listaGrpGeneralUninegB.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarB : listaGrpGeneralUninegB) {
                    res = Math.round(resGrpGeneralbarB.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarB.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarB.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarB.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarB.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarB.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarB.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarB.getSatisfecho().floatValue())));    
                        resGrpGeneralbarB.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarB.getNombre(), resGrpGeneralbarB.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarB.getNombre(), resGrpGeneralbarB.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarB.getNombre(), resGrpGeneralbarB.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegB.setTitle("Organización, roles y funciones por grupos ocupacionales");
                barGrpGeneralUninegB.setLegendPosition("ne");
                barGrpGeneralUninegB.setShowPointLabels(true);
                barGrpGeneralUninegB.setAnimate(true);
                barGrpGeneralUninegB.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegB.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegB.addSeries(satisfecho);
        barGrpGeneralUninegB.addSeries(med_satisfecho);
        barGrpGeneralUninegB.addSeries(no_satisfecho);
        return barGrpGeneralUninegB;
    }       
    
 /********************************* DIMENSION  C *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarC() {
        barGeneralUninegC = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegC = resDimensionService.sp_mc_sat_general_unineg("C",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegC.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegC.isEmpty()) {
                for (ConGeneralBean resGeneralbarC : listaGeneralUninegC) {
                    res = Math.round(resGeneralbarC.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarC.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarC.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarC.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarC.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarC.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarC.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarC.getSatisfecho().floatValue())));    
                        resGeneralbarC.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarC.getNombre(), resGeneralbarC.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarC.getNombre(), resGeneralbarC.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarC.getNombre(), resGeneralbarC.getSatisfecho().floatValue());
                }
                barGeneralUninegC.setTitle("Trabajo en equipo");
                barGeneralUninegC.setLegendPosition("ne");
                barGeneralUninegC.setShowPointLabels(true);
                barGeneralUninegC.setAnimate(true);
                barGeneralUninegC.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegC.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegC.addSeries(satisfecho);
        barGeneralUninegC.addSeries(med_satisfecho);
        barGeneralUninegC.addSeries(no_satisfecho);
        return barGeneralUninegC;
    }       

  /*** DIMENSION   C   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarC() {
        barGrpGeneralUninegC = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegC = resDimensionService.sp_mc_sat_general_dimensiones("C",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegC.size());
            if (!listaGrpGeneralUninegC.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarC : listaGrpGeneralUninegC) {
                    res = Math.round(resGrpGeneralbarC.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarC.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarC.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarC.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarC.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarC.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarC.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarC.getSatisfecho().floatValue())));    
                        resGrpGeneralbarC.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarC.getNombre(), resGrpGeneralbarC.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarC.getNombre(), resGrpGeneralbarC.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarC.getNombre(), resGrpGeneralbarC.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegC.setTitle("Trabajo en equipo por grupos ocupacionales");
                barGrpGeneralUninegC.setLegendPosition("ne");
                barGrpGeneralUninegC.setShowPointLabels(true);
                barGrpGeneralUninegC.setAnimate(true);
                barGrpGeneralUninegC.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegC.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegC.addSeries(satisfecho);
        barGrpGeneralUninegC.addSeries(med_satisfecho);
        barGrpGeneralUninegC.addSeries(no_satisfecho);
        return barGrpGeneralUninegC;
    }      
    
     /********************************* DIMENSION  D *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarD() {
        barGeneralUninegD = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegD = resDimensionService.sp_mc_sat_general_unineg("D",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegD.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegD.isEmpty()) {
                for (ConGeneralBean resGeneralbarD : listaGeneralUninegD) {
                    res = Math.round(resGeneralbarD.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarD.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarD.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarD.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarD.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarD.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarD.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarD.getSatisfecho().floatValue())));    
                        resGeneralbarD.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarD.getNombre(), resGeneralbarD.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarD.getNombre(), resGeneralbarD.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarD.getNombre(), resGeneralbarD.getSatisfecho().floatValue());
                }
                barGeneralUninegD.setTitle("Relaciones interpersonales");
                barGeneralUninegD.setLegendPosition("ne");
                barGeneralUninegD.setShowPointLabels(true);
                barGeneralUninegD.setAnimate(true);
                barGeneralUninegD.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegD.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegD.addSeries(satisfecho);
        barGeneralUninegD.addSeries(med_satisfecho);
        barGeneralUninegD.addSeries(no_satisfecho);
        return barGeneralUninegD;
    }       

  /*** DIMENSION   D   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarD() {
        barGrpGeneralUninegD = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegD = resDimensionService.sp_mc_sat_general_dimensiones("D",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegD.size());
            if (!listaGrpGeneralUninegD.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarD : listaGrpGeneralUninegD) {
                    res = Math.round(resGrpGeneralbarD.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarD.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarD.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarD.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarD.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarD.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarD.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarD.getSatisfecho().floatValue())));    
                        resGrpGeneralbarD.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarD.getNombre(), resGrpGeneralbarD.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarD.getNombre(), resGrpGeneralbarD.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarD.getNombre(), resGrpGeneralbarD.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegD.setTitle("Relaciones interpersonales por grupos ocupacionales");
                barGrpGeneralUninegD.setLegendPosition("ne");
                barGrpGeneralUninegD.setShowPointLabels(true);
                barGrpGeneralUninegD.setAnimate(true);
                barGrpGeneralUninegD.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegD.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegD.addSeries(satisfecho);
        barGrpGeneralUninegD.addSeries(med_satisfecho);
        barGrpGeneralUninegD.addSeries(no_satisfecho);
        return barGrpGeneralUninegD;
    }  
  /********************************* DIMENSION  E *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarE() {
        barGeneralUninegE = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegE = resDimensionService.sp_mc_sat_general_unineg("E",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegE.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegE.isEmpty()) {
                for (ConGeneralBean resGeneralbarE : listaGeneralUninegE) {
                    res = Math.round(resGeneralbarE.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarE.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarE.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarE.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarE.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarE.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarE.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarE.getSatisfecho().floatValue())));    
                        resGeneralbarE.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarE.getNombre(), resGeneralbarE.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarE.getNombre(), resGeneralbarE.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarE.getNombre(), resGeneralbarE.getSatisfecho().floatValue());
                }
                barGeneralUninegE.setTitle("Manejo de conflictos");
                barGeneralUninegE.setLegendPosition("ne");
                barGeneralUninegE.setShowPointLabels(true);
                barGeneralUninegE.setAnimate(true);
                barGeneralUninegE.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegE.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegE.addSeries(satisfecho);
        barGeneralUninegE.addSeries(med_satisfecho);
        barGeneralUninegE.addSeries(no_satisfecho);
        return barGeneralUninegE;
    }       

  /*** DIMENSION   E   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarE() {
        barGrpGeneralUninegE = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
		
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegE = resDimensionService.sp_mc_sat_general_dimensiones("E",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegE.size());
            if (!listaGrpGeneralUninegE.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarE : listaGrpGeneralUninegE) {
                    res = Math.round(resGrpGeneralbarE.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarE.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarE.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarE.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarE.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarE.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarE.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarE.getSatisfecho().floatValue())));    
                        resGrpGeneralbarE.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarE.getNombre(), resGrpGeneralbarE.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarE.getNombre(), resGrpGeneralbarE.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarE.getNombre(), resGrpGeneralbarE.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegE.setTitle("Manejo de conflictos por grupos ocupacionales");
                barGrpGeneralUninegE.setLegendPosition("ne");
                barGrpGeneralUninegE.setShowPointLabels(true);
                barGrpGeneralUninegE.setAnimate(true);
                barGrpGeneralUninegE.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegE.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegE.addSeries(satisfecho);
        barGrpGeneralUninegE.addSeries(med_satisfecho);
        barGrpGeneralUninegE.addSeries(no_satisfecho);
        return barGrpGeneralUninegE;
    }     
    
 /********************************* DIMENSION  F *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarF() {
        barGeneralUninegF = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
		
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegF = resDimensionService.sp_mc_sat_general_unineg("F",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegF.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegF.isEmpty()) {
                for (ConGeneralBean resGeneralbarF : listaGeneralUninegF) {
                     res = Math.round(resGeneralbarF.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarF.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarF.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarF.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarF.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarF.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarF.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarF.getSatisfecho().floatValue())));    
                        resGeneralbarF.setMedSatisfecho(newval);
                    }                   
                    no_satisfecho.set(resGeneralbarF.getNombre(), resGeneralbarF.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarF.getNombre(), resGeneralbarF.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarF.getNombre(), resGeneralbarF.getSatisfecho().floatValue());
                }
                barGeneralUninegF.setTitle("Orgullo y Compromiso");
                barGeneralUninegF.setLegendPosition("ne");
                barGeneralUninegF.setShowPointLabels(true);
                barGeneralUninegF.setAnimate(true);
                barGeneralUninegF.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegF.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegF.addSeries(satisfecho);
        barGeneralUninegF.addSeries(med_satisfecho);
        barGeneralUninegF.addSeries(no_satisfecho);
        return barGeneralUninegF;
    }       

  /*** DIMENSION   F   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarF() {
        barGrpGeneralUninegF = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegF = resDimensionService.sp_mc_sat_general_dimensiones("F",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegF.size());
            if (!listaGrpGeneralUninegF.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarF : listaGrpGeneralUninegF) {
                    res = Math.round(resGrpGeneralbarF.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarF.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarF.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarF.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarF.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarF.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarF.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarF.getSatisfecho().floatValue())));    
                        resGrpGeneralbarF.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarF.getNombre(), resGrpGeneralbarF.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarF.getNombre(), resGrpGeneralbarF.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarF.getNombre(), resGrpGeneralbarF.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegF.setTitle("Orgullo y Compromiso por grupos ocupacionales");
                barGrpGeneralUninegF.setLegendPosition("ne");
                barGrpGeneralUninegF.setShowPointLabels(true);
                barGrpGeneralUninegF.setAnimate(true);
                barGrpGeneralUninegF.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegF.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegF.addSeries(satisfecho);
        barGrpGeneralUninegF.addSeries(med_satisfecho);
        barGrpGeneralUninegF.addSeries(no_satisfecho);
        return barGrpGeneralUninegF;
    }      
    
 /********************************* DIMENSION  G *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarG() {
        barGeneralUninegG = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegG = resDimensionService.sp_mc_sat_general_unineg("G",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegG.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegG.isEmpty()) {
                for (ConGeneralBean resGeneralbarG : listaGeneralUninegG) {
                    res = Math.round(resGeneralbarG.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarG.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarG.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarG.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarG.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarG.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarG.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarG.getSatisfecho().floatValue())));    
                        resGeneralbarG.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarG.getNombre(), resGeneralbarG.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarG.getNombre(), resGeneralbarG.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarG.getNombre(), resGeneralbarG.getSatisfecho().floatValue());
                }
                barGeneralUninegG.setTitle("Comunicación");
                barGeneralUninegG.setLegendPosition("ne");
                barGeneralUninegG.setShowPointLabels(true);
                barGeneralUninegG.setAnimate(true);
                barGeneralUninegG.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegG.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegG.addSeries(satisfecho);
        barGeneralUninegG.addSeries(med_satisfecho);
        barGeneralUninegG.addSeries(no_satisfecho);
        return barGeneralUninegG;
    }       

  /*** DIMENSION   G   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarG() {
        barGrpGeneralUninegG = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegG = resDimensionService.sp_mc_sat_general_dimensiones("G",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegG.size());
            if (!listaGrpGeneralUninegG.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarG : listaGrpGeneralUninegG) {
                    res = Math.round(resGrpGeneralbarG.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarG.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarG.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarG.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarG.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarG.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarG.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarG.getSatisfecho().floatValue())));    
                        resGrpGeneralbarG.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarG.getNombre(), resGrpGeneralbarG.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarG.getNombre(), resGrpGeneralbarG.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarG.getNombre(), resGrpGeneralbarG.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegG.setTitle("Comunicación por grupos ocupacionales");
                barGrpGeneralUninegG.setLegendPosition("ne");
                barGrpGeneralUninegG.setShowPointLabels(true);
                barGrpGeneralUninegG.setAnimate(true);
                barGrpGeneralUninegG.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegG.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegG.addSeries(satisfecho);
        barGrpGeneralUninegG.addSeries(med_satisfecho);
        barGrpGeneralUninegG.addSeries(no_satisfecho);
        return barGrpGeneralUninegG;
    }      
    
 /********************************* DIMENSION  H *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarH() {
        barGeneralUninegH = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegH = resDimensionService.sp_mc_sat_general_unineg("H",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegH.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegH.isEmpty()) {
                for (ConGeneralBean resGeneralbarH : listaGeneralUninegH) {
                    res = Math.round(resGeneralbarH.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarH.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarH.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarH.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarH.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarH.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarH.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarH.getSatisfecho().floatValue())));    
                        resGeneralbarH.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarH.getNombre(), resGeneralbarH.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarH.getNombre(), resGeneralbarH.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarH.getNombre(), resGeneralbarH.getSatisfecho().floatValue());
                }
                barGeneralUninegH.setTitle("Reconocimiento y beneficios");
                barGeneralUninegH.setLegendPosition("ne");
                barGeneralUninegH.setShowPointLabels(true);
                barGeneralUninegH.setAnimate(true);
                barGeneralUninegH.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegH.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegH.addSeries(satisfecho);
        barGeneralUninegH.addSeries(med_satisfecho);
        barGeneralUninegH.addSeries(no_satisfecho);
        return barGeneralUninegH;
    }       

  /*** DIMENSION   H   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarH() {
        barGrpGeneralUninegH = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegH = resDimensionService.sp_mc_sat_general_dimensiones("H",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegH.size());
            if (!listaGrpGeneralUninegH.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarH : listaGrpGeneralUninegH) {
                    res = Math.round(resGrpGeneralbarH.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarH.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarH.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarH.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarH.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarH.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarH.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarH.getSatisfecho().floatValue())));    
                        resGrpGeneralbarH.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarH.getNombre(), resGrpGeneralbarH.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarH.getNombre(), resGrpGeneralbarH.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarH.getNombre(), resGrpGeneralbarH.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegH.setTitle("Reconocimiento y beneficios por grupos ocupacionales");
                barGrpGeneralUninegH.setLegendPosition("ne");
                barGrpGeneralUninegH.setShowPointLabels(true);
                barGrpGeneralUninegH.setAnimate(true);
                barGrpGeneralUninegH.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegH.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegH.addSeries(satisfecho);
        barGrpGeneralUninegH.addSeries(med_satisfecho);
        barGrpGeneralUninegH.addSeries(no_satisfecho);
        return barGrpGeneralUninegH;
    }      
    
 /********************************* DIMENSION  I *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarI() {
        barGeneralUninegI = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegI = resDimensionService.sp_mc_sat_general_unineg("I",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegI.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegI.isEmpty()) {
                for (ConGeneralBean resGeneralbarI : listaGeneralUninegI) {
                    res = Math.round(resGeneralbarI.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarI.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarI.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarI.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarI.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarI.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarI.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarI.getSatisfecho().floatValue())));    
                        resGeneralbarI.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarI.getNombre(), resGeneralbarI.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarI.getNombre(), resGeneralbarI.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarI.getNombre(), resGeneralbarI.getSatisfecho().floatValue());
                }
                barGeneralUninegI.setTitle("Capacitación y Desarrollo");
                barGeneralUninegI.setLegendPosition("ne");
                barGeneralUninegI.setShowPointLabels(true);
                barGeneralUninegI.setAnimate(true);
                barGeneralUninegI.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegI.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegI.addSeries(satisfecho);
        barGeneralUninegI.addSeries(med_satisfecho);
        barGeneralUninegI.addSeries(no_satisfecho);
        return barGeneralUninegI;
    }       

  /*** DIMENSION   I   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarI() {
        barGrpGeneralUninegI = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegI = resDimensionService.sp_mc_sat_general_dimensiones("I",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegI.size());
            if (!listaGrpGeneralUninegI.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarI : listaGrpGeneralUninegI) {
                    res = Math.round(resGrpGeneralbarI.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarI.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarI.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarI.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarI.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarI.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarI.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarI.getSatisfecho().floatValue())));    
                        resGrpGeneralbarI.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarI.getNombre(), resGrpGeneralbarI.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarI.getNombre(), resGrpGeneralbarI.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarI.getNombre(), resGrpGeneralbarI.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegI.setTitle("Capacitación y Desarrollo por grupos ocupacionales");
                barGrpGeneralUninegI.setLegendPosition("ne");
                barGrpGeneralUninegI.setShowPointLabels(true);
                barGrpGeneralUninegI.setAnimate(true);
                barGrpGeneralUninegI.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegI.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegI.addSeries(satisfecho);
        barGrpGeneralUninegI.addSeries(med_satisfecho);
        barGrpGeneralUninegI.addSeries(no_satisfecho);
        return barGrpGeneralUninegI;
    }      
    
 /********************************* DIMENSION  J *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarJ() {
        barGeneralUninegJ = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegJ = resDimensionService.sp_mc_sat_general_unineg("J",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegJ.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegJ.isEmpty()) {
                for (ConGeneralBean resGeneralbarJ : listaGeneralUninegJ) {
                    res = Math.round(resGeneralbarJ.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarJ.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarJ.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarJ.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarJ.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarJ.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarJ.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarJ.getSatisfecho().floatValue())));    
                        resGeneralbarJ.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarJ.getNombre(), resGeneralbarJ.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarJ.getNombre(), resGeneralbarJ.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarJ.getNombre(), resGeneralbarJ.getSatisfecho().floatValue());
                }
                barGeneralUninegJ.setTitle("Carga de trabajo y Remuneración");
                barGeneralUninegJ.setLegendPosition("ne");
                barGeneralUninegJ.setShowPointLabels(true);
                barGeneralUninegJ.setAnimate(true);
                barGeneralUninegJ.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegJ.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegJ.addSeries(satisfecho);
        barGeneralUninegJ.addSeries(med_satisfecho);
        barGeneralUninegJ.addSeries(no_satisfecho);
        return barGeneralUninegJ;
    }       

  /*** DIMENSION   J   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarJ() {
        barGrpGeneralUninegJ = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegJ = resDimensionService.sp_mc_sat_general_dimensiones("J",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegJ.size());
            if (!listaGrpGeneralUninegJ.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarJ : listaGrpGeneralUninegJ) {
                    res = Math.round(resGrpGeneralbarJ.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarJ.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarJ.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarJ.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarJ.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarJ.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarJ.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarJ.getSatisfecho().floatValue())));    
                        resGrpGeneralbarJ.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarJ.getNombre(), resGrpGeneralbarJ.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarJ.getNombre(), resGrpGeneralbarJ.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarJ.getNombre(), resGrpGeneralbarJ.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegJ.setTitle("Carga de trabajo y Remuneración por grupos ocupacionales");
                barGrpGeneralUninegJ.setLegendPosition("ne");
                barGrpGeneralUninegJ.setShowPointLabels(true);
                barGrpGeneralUninegJ.setAnimate(true);
                barGrpGeneralUninegJ.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegJ.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegJ.addSeries(satisfecho);
        barGrpGeneralUninegJ.addSeries(med_satisfecho);
        barGrpGeneralUninegJ.addSeries(no_satisfecho);
        return barGrpGeneralUninegJ;
    }      
    
 /********************************* DIMENSION  K *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarK() {
        barGeneralUninegK = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegK = resDimensionService.sp_mc_sat_general_unineg("K",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegK.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegK.isEmpty()) {
                for (ConGeneralBean resGeneralbarK : listaGeneralUninegK) {
                    res = Math.round(resGeneralbarK.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarK.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarK.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarK.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarK.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarK.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarK.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarK.getSatisfecho().floatValue())));    
                        resGeneralbarK.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarK.getNombre(), resGeneralbarK.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarK.getNombre(), resGeneralbarK.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarK.getNombre(), resGeneralbarK.getSatisfecho().floatValue());
                }
                barGeneralUninegK.setTitle("Liderazgo");
                barGeneralUninegK.setLegendPosition("ne");
                barGeneralUninegK.setShowPointLabels(true);
                barGeneralUninegK.setAnimate(true);
                barGeneralUninegK.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegK.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegK.addSeries(satisfecho);
        barGeneralUninegK.addSeries(med_satisfecho);
        barGeneralUninegK.addSeries(no_satisfecho);
        return barGeneralUninegK;
    }       

  /*** DIMENSION   K   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarK() {
        barGrpGeneralUninegK = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegK = resDimensionService.sp_mc_sat_general_dimensiones("K",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegK.size());
            if (!listaGrpGeneralUninegK.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarK : listaGrpGeneralUninegK) {
                    res = Math.round(resGrpGeneralbarK.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarK.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarK.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarK.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarK.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarK.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarK.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarK.getSatisfecho().floatValue())));    
                        resGrpGeneralbarK.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarK.getNombre(), resGrpGeneralbarK.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarK.getNombre(), resGrpGeneralbarK.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarK.getNombre(), resGrpGeneralbarK.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegK.setTitle("Liderazgo por grupos ocupacionales");
                barGrpGeneralUninegK.setLegendPosition("ne");
                barGrpGeneralUninegK.setShowPointLabels(true);
                barGrpGeneralUninegK.setAnimate(true);
                barGrpGeneralUninegK.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegK.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegK.addSeries(satisfecho);
        barGrpGeneralUninegK.addSeries(med_satisfecho);
        barGrpGeneralUninegK.addSeries(no_satisfecho);
        return barGrpGeneralUninegK;
    }      
    
 /********************************* DIMENSION  L *************************************************************/   
    public BarChartModel obtenersatGeneralUniNegBarL() {
        barGeneralUninegL = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGeneralUninegL = resDimensionService.sp_mc_sat_general_unineg("L",anio);
            System.out.println("lista CONSOLIDADO general ==> " + listaGeneralUninegL.size());
            /*String  val_no_satisfecho;*/
            if (!listaGeneralUninegL.isEmpty()) {
                for (ConGeneralBean resGeneralbarL : listaGeneralUninegL) {
                    res = Math.round(resGeneralbarL.getNoSatisfecho().floatValue())  + 
						  Math.round(resGeneralbarL.getMedSatisfecho().floatValue()) +  
						  Math.round(resGeneralbarL.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGeneralbarL.getNoSatisfecho().floatValue()) + 
						Math.round(resGeneralbarL.getMedSatisfecho().floatValue()))  ; 
                        resGeneralbarL.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGeneralbarL.getNoSatisfecho().floatValue()) +
						Math.round(resGeneralbarL.getSatisfecho().floatValue())));    
                        resGeneralbarL.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGeneralbarL.getNombre(), resGeneralbarL.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGeneralbarL.getNombre(), resGeneralbarL.getMedSatisfecho().floatValue());
                    satisfecho.set(resGeneralbarL.getNombre(), resGeneralbarL.getSatisfecho().floatValue());
                }
                barGeneralUninegL.setTitle("Carisma Marista");
                barGeneralUninegL.setLegendPosition("ne");
                barGeneralUninegL.setShowPointLabels(true);
                barGeneralUninegL.setAnimate(true);
                barGeneralUninegL.setExtender("chartBarSatisfaccionExtender");
                barGeneralUninegL.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGeneralUninegL.addSeries(satisfecho);
        barGeneralUninegL.addSeries(med_satisfecho);
        barGeneralUninegL.addSeries(no_satisfecho);
        return barGeneralUninegL;
    }       

  /*** DIMENSION   L   - POR GRUPOS OCUPACIONALES *****************************************************/
    public BarChartModel obtenerGrpGeneralUniNegBarL() {
        barGrpGeneralUninegL = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpGeneralUninegL = resDimensionService.sp_mc_sat_general_dimensiones("L",anio);
            System.out.println("lista CONSOLIDADO general por grupos ==> " + listaGrpGeneralUninegL.size());
            if (!listaGrpGeneralUninegL.isEmpty()) {
                for (ConGeneralBean resGrpGeneralbarL : listaGrpGeneralUninegL) {
                    res = Math.round(resGrpGeneralbarL.getNoSatisfecho().floatValue())  + 
						  Math.round(resGrpGeneralbarL.getMedSatisfecho().floatValue()) +  
						  Math.round(resGrpGeneralbarL.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resGrpGeneralbarL.getNoSatisfecho().floatValue()) + 
						Math.round(resGrpGeneralbarL.getMedSatisfecho().floatValue()))  ; 
                        resGrpGeneralbarL.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resGrpGeneralbarL.getNoSatisfecho().floatValue()) +
						Math.round(resGrpGeneralbarL.getSatisfecho().floatValue())));    
                        resGrpGeneralbarL.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(resGrpGeneralbarL.getNombre(), resGrpGeneralbarL.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resGrpGeneralbarL.getNombre(), resGrpGeneralbarL.getMedSatisfecho().floatValue());
                    satisfecho.set(resGrpGeneralbarL.getNombre(), resGrpGeneralbarL.getSatisfecho().floatValue());
                }
                barGrpGeneralUninegL.setTitle("Carisma Marista por grupos ocupacionales");
                barGrpGeneralUninegL.setLegendPosition("ne");
                barGrpGeneralUninegL.setShowPointLabels(true);
                barGrpGeneralUninegL.setAnimate(true);
                barGrpGeneralUninegL.setExtender("chartBarSatisfaccionExtender");
                barGrpGeneralUninegL.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpGeneralUninegL.addSeries(satisfecho);
        barGrpGeneralUninegL.addSeries(med_satisfecho);
        barGrpGeneralUninegL.addSeries(no_satisfecho);
        return barGrpGeneralUninegL;
    }      
    
 
////////////////////////////// SATISFACCION GENERAL ANIO ACTUAL ////////////////////////////////////////////
    public BarChartModel obtenerSatisfaccionGrlBar() {
        barSatisfaccionGral = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0;
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatisfaccionGral = resDimensionService.sp_mc_satisfaccion_general_actual(anio);
            System.out.println("Lista  de historico =====>" + listaSatisfaccionGral.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatisfaccionGral.isEmpty()) {
                for (SatisfaccionGrlBean satisfaccionGrl : listaSatisfaccionGral) {
                    res = Math.round(satisfaccionGrl.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionGrl.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionGrl.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionGrl.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionGrl.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionGrl.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionGrl.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionGrl.getPorSatisfecho().floatValue())));    
                        satisfaccionGrl.setPorMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(satisfaccionGrl.getNombre(), satisfaccionGrl.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionGrl.getNombre(), satisfaccionGrl.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionGrl.getNombre(), satisfaccionGrl.getPorSatisfecho().floatValue());
                }
                //barSatisfaccionGral.setTitle("");
                barSatisfaccionGral.setLegendPosition("ne");
                barSatisfaccionGral.setStacked(true);
                barSatisfaccionGral.setShowPointLabels(true);
                barSatisfaccionGral.setAnimate(true);
                barSatisfaccionGral.setExtender("chartBarHistoricoExtender");
                barSatisfaccionGral.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatisfaccionGral.addSeries(satisfecho);
        barSatisfaccionGral.addSeries(med_satisfecho);
        barSatisfaccionGral.addSeries(no_satisfecho);
        return barSatisfaccionGral;
    }

    public BarChartModel obtenerSatisfaccionGrlBar_est_part(String tip_colegio) {
        barSatisfaccionGral_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0;
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatisfaccionGral_est_part = resDimensionService.sp_mc_satisfaccion_general_actual_estatal_particular(tip_colegio,anio);
            System.out.println("Lista  de historico =====>" + listaSatisfaccionGral_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatisfaccionGral_est_part.isEmpty()) {
                for (SatisfaccionGrlBean satisfaccionGrl : listaSatisfaccionGral_est_part) {
                    res = Math.round(satisfaccionGrl.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionGrl.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionGrl.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionGrl.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionGrl.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionGrl.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionGrl.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionGrl.getPorSatisfecho().floatValue())));    
                        satisfaccionGrl.setPorMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(satisfaccionGrl.getNombre(), satisfaccionGrl.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionGrl.getNombre(), satisfaccionGrl.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionGrl.getNombre(), satisfaccionGrl.getPorSatisfecho().floatValue());
                }
                //barSatisfaccionGral.setTitle("");
                barSatisfaccionGral_est_part.setLegendPosition("ne");
                barSatisfaccionGral_est_part.setStacked(true);
                barSatisfaccionGral_est_part.setShowPointLabels(true);
                barSatisfaccionGral_est_part.setAnimate(true);
                barSatisfaccionGral_est_part.setExtender("chartBarHistoricoExtender");
                barSatisfaccionGral_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatisfaccionGral_est_part.addSeries(satisfecho);
        barSatisfaccionGral_est_part.addSeries(med_satisfecho);
        barSatisfaccionGral_est_part.addSeries(no_satisfecho);
        return barSatisfaccionGral_est_part;
    }
    
    public BarChartModel obtenerSatisfaccionGrlBar_est_par(String tip_colegio) {
        barSatisfaccionGral_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0;
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatisfaccionGral_est_part = resDimensionService.sp_mc_satisfaccion_general_actual_estatal_particular(tip_colegio,anio);
            System.out.println("Lista  de historico =====>" + listaSatisfaccionGral_est_part.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatisfaccionGral_est_part.isEmpty()) {
                for (SatisfaccionGrlBean satisfaccionGrl_est_part : listaSatisfaccionGral_est_part) {
                    res = Math.round(satisfaccionGrl_est_part.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionGrl_est_part.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionGrl_est_part.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionGrl_est_part.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionGrl_est_part.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionGrl_est_part.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionGrl_est_part.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionGrl_est_part.getPorSatisfecho().floatValue())));    
                        satisfaccionGrl_est_part.setPorMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(satisfaccionGrl_est_part.getNombre(), satisfaccionGrl_est_part.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionGrl_est_part.getNombre(), satisfaccionGrl_est_part.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionGrl_est_part.getNombre(), satisfaccionGrl_est_part.getPorSatisfecho().floatValue());
                }
                //barSatisfaccionGral.setTitle("");
                barSatisfaccionGral_est_part.setLegendPosition("ne");
                barSatisfaccionGral_est_part.setStacked(true);
                barSatisfaccionGral_est_part.setShowPointLabels(true);
                barSatisfaccionGral_est_part.setAnimate(true);
                barSatisfaccionGral_est_part.setExtender("chartBarHistoricoExtender");
                barSatisfaccionGral_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatisfaccionGral_est_part.addSeries(satisfecho);
        barSatisfaccionGral_est_part.addSeries(med_satisfecho);
        barSatisfaccionGral_est_part.addSeries(no_satisfecho);
        return barSatisfaccionGral_est_part;
    }        
///////////////////////// SATISFACCION GENERAL UNIDADES DE NEGOCIO ///////////////////////////////////////////////////////////        
        public BarChartModel obtenerSatHistoricoBarSECTOR() {
         
        barSatHisSECTOR = new BarChartModel();
        String unineg ="SECTOR";
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisSECTOR = resDimensionService.sp_mc_general_historico(unineg);
            System.out.println("Lista  de historico =====>" + listaSatHisSECTOR.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisSECTOR.isEmpty()) {
                for (HistoricoBean satisfaccionHisSECTOR : listaSatHisSECTOR) {
                    res = Math.round(satisfaccionHisSECTOR.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisSECTOR.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisSECTOR.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisSECTOR.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisSECTOR.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisSECTOR.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisSECTOR.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisSECTOR.getPorSatisfecho().floatValue())));    
                        satisfaccionHisSECTOR.setPorMedSatisfecho(newval);
                    }                      
                    no_satisfecho.set(satisfaccionHisSECTOR.getAnio(), satisfaccionHisSECTOR.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisSECTOR.getAnio(), satisfaccionHisSECTOR.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisSECTOR.getAnio(), satisfaccionHisSECTOR.getPorSatisfecho().floatValue());
                }
                barSatHisSECTOR.setTitle("SECTOR");
                barSatHisSECTOR.setLegendPosition("ne");
                barSatHisSECTOR.setStacked(true);
                barSatHisSECTOR.setShowPointLabels(true);
                barSatHisSECTOR.setAnimate(true);
                barSatHisSECTOR.setExtender("chartBarHistoricoExtender");
                barSatHisSECTOR.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisSECTOR.addSeries(satisfecho);
        barSatHisSECTOR.addSeries(med_satisfecho);
        barSatHisSECTOR.addSeries(no_satisfecho);
        return barSatHisSECTOR;
    }
    
    
    public BarChartModel obtenerSatHistoricoBarSANLUI() {
        
        barSatHisSANLUI = new BarChartModel();
        String unineg ="SANLUI";
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisSANLUI = resDimensionService.sp_mc_general_historico(unineg);
            System.out.println("Lista  de historico =====>" + listaSatHisSANLUI.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisSANLUI.isEmpty()) {
                for (HistoricoBean satisfaccionHisSANLUI : listaSatHisSANLUI) {
                    res = Math.round(satisfaccionHisSANLUI.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisSANLUI.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisSANLUI.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisSANLUI.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisSANLUI.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisSANLUI.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisSANLUI.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisSANLUI.getPorSatisfecho().floatValue())));    
                        satisfaccionHisSANLUI.setPorMedSatisfecho(newval);
                    }                      
                    no_satisfecho.set(satisfaccionHisSANLUI.getAnio(), satisfaccionHisSANLUI.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisSANLUI.getAnio(), satisfaccionHisSANLUI.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisSANLUI.getAnio(), satisfaccionHisSANLUI.getPorSatisfecho().floatValue());
                }
                barSatHisSANLUI.setTitle("Colegio San Luis de Barranco");
                barSatHisSANLUI.setLegendPosition("ne");
                barSatHisSANLUI.setStacked(true);
                barSatHisSANLUI.setShowPointLabels(true);
                barSatHisSANLUI.setAnimate(true);
                barSatHisSANLUI.setExtender("chartBarHistoricoExtender");
                barSatHisSANLUI.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisSANLUI.addSeries(satisfecho);
        barSatHisSANLUI.addSeries(med_satisfecho);
        barSatHisSANLUI.addSeries(no_satisfecho);
        return barSatHisSANLUI;
    }        
        
    public BarChartModel obtenerSatHistoricoBarBARINA() {
        
        barSatHisBARINA = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisBARINA = resDimensionService.sp_mc_general_historico("BARINA");
            System.out.println("Lista  de historico =====>" + listaSatHisBARINA.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisBARINA.isEmpty()) {
                for (HistoricoBean satisfaccionHisBARINA : listaSatHisBARINA) {
                    res = Math.round(satisfaccionHisBARINA.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisBARINA.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisBARINA.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisBARINA.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisBARINA.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisBARINA.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisBARINA.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisBARINA.getPorSatisfecho().floatValue())));    
                        satisfaccionHisBARINA.setPorMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(satisfaccionHisBARINA.getAnio(), satisfaccionHisBARINA.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisBARINA.getAnio(), satisfaccionHisBARINA.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisBARINA.getAnio(), satisfaccionHisBARINA.getPorSatisfecho().floatValue());
                }
                barSatHisBARINA.setTitle("Colegio Manuel  R. Barinaga de SJM");
                barSatHisBARINA.setLegendPosition("ne");
                barSatHisBARINA.setStacked(true);
                barSatHisBARINA.setShowPointLabels(true);
                barSatHisBARINA.setAnimate(true);
                barSatHisBARINA.setExtender("chartBarHistoricoExtender");
                barSatHisBARINA.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisBARINA.addSeries(satisfecho);
        barSatHisBARINA.addSeries(med_satisfecho);
        barSatHisBARINA.addSeries(no_satisfecho);
        return barSatHisBARINA;
    }        
    
   public BarChartModel obtenerSatHistoricoBarCHAMPC() {
        
        barSatHisCHAMPC= new BarChartModel();
        
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisCHAMPC = resDimensionService.sp_mc_general_historico("CHAMPC");
            System.out.println("Lista  de historico =====>" + listaSatHisCHAMPC.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisCHAMPC.isEmpty()) {
                for (HistoricoBean satisfaccionHisCHAMPC : listaSatHisCHAMPC) {
                    res = Math.round(satisfaccionHisCHAMPC.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisCHAMPC.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisCHAMPC.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisCHAMPC.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisCHAMPC.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisCHAMPC.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisCHAMPC.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisCHAMPC.getPorSatisfecho().floatValue())));    
                        satisfaccionHisCHAMPC.setPorMedSatisfecho(newval);
                    }                     
                    no_satisfecho.set(satisfaccionHisCHAMPC.getAnio(), satisfaccionHisCHAMPC.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisCHAMPC.getAnio(), satisfaccionHisCHAMPC.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisCHAMPC.getAnio(), satisfaccionHisCHAMPC.getPorSatisfecho().floatValue());
                }
                barSatHisCHAMPC.setTitle("Colegio Champagnat de Chosica");
                barSatHisCHAMPC.setLegendPosition("ne");
                barSatHisCHAMPC.setStacked(true);
                barSatHisCHAMPC.setShowPointLabels(true);
                barSatHisCHAMPC.setAnimate(true);
                barSatHisCHAMPC.setExtender("chartBarHistoricoExtender");
                barSatHisCHAMPC.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisCHAMPC.addSeries(satisfecho);
        barSatHisCHAMPC.addSeries(med_satisfecho);
        barSatHisCHAMPC.addSeries(no_satisfecho);
        return barSatHisCHAMPC;
    }        
        
    public BarChartModel obtenerSatHistoricoBarCHAMPS() {
        
        barSatHisCHAMPS = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisCHAMPS = resDimensionService.sp_mc_general_historico("CHAMPS");
            System.out.println("Lista  de historico =====>" + listaSatHisCHAMPS.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisCHAMPS.isEmpty()) {
                for (HistoricoBean satisfaccionHisCHAMPS : listaSatHisCHAMPS) {
                    res = Math.round(satisfaccionHisCHAMPS.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisCHAMPS.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisCHAMPS.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisCHAMPS.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisCHAMPS.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisCHAMPS.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisCHAMPS.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisCHAMPS.getPorSatisfecho().floatValue())));    
                        satisfaccionHisCHAMPS.setPorMedSatisfecho(newval);
                    }                     
                    no_satisfecho.set(satisfaccionHisCHAMPS.getAnio(), satisfaccionHisCHAMPS.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisCHAMPS.getAnio(), satisfaccionHisCHAMPS.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisCHAMPS.getAnio(), satisfaccionHisCHAMPS.getPorSatisfecho().floatValue());
                }
                barSatHisCHAMPS.setTitle("Colegio Champagnat de Surco");
                barSatHisCHAMPS.setLegendPosition("ne");
                barSatHisCHAMPS.setStacked(true);
                barSatHisCHAMPS.setShowPointLabels(true);
                barSatHisCHAMPS.setAnimate(true);
                barSatHisCHAMPS.setExtender("chartBarHistoricoExtender");
                barSatHisCHAMPS.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisCHAMPS.addSeries(satisfecho);
        barSatHisCHAMPS.addSeries(med_satisfecho);
        barSatHisCHAMPS.addSeries(no_satisfecho);
        return barSatHisCHAMPS;
    }     

    
     public BarChartModel obtenerSatHistoricoBarCRISTC() {
        
        barSatHisCRISTC = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisCRISTC = resDimensionService.sp_mc_general_historico("CRISTC");
            System.out.println("Lista  de historico =====>" + listaSatHisCRISTC.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisCRISTC.isEmpty()) {
                for (HistoricoBean satisfaccionHisCRISTC : listaSatHisCRISTC) {
                    res = Math.round(satisfaccionHisCRISTC.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisCRISTC.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisCRISTC.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisCRISTC.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisCRISTC.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisCRISTC.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisCRISTC.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisCRISTC.getPorSatisfecho().floatValue())));    
                        satisfaccionHisCRISTC.setPorMedSatisfecho(newval);
                    }                     
                    no_satisfecho.set(satisfaccionHisCRISTC.getAnio(), satisfaccionHisCRISTC.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisCRISTC.getAnio(), satisfaccionHisCRISTC.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisCRISTC.getAnio(), satisfaccionHisCRISTC.getPorSatisfecho().floatValue());
                }
                barSatHisCRISTC.setTitle("Cristo Rey de Cajamarca");
                barSatHisCRISTC.setLegendPosition("ne");
                barSatHisCRISTC.setStacked(true);
                barSatHisCRISTC.setShowPointLabels(true);
                barSatHisCRISTC.setAnimate(true);
                barSatHisCRISTC.setExtender("chartBarHistoricoExtender");
                barSatHisCRISTC.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisCRISTC.addSeries(satisfecho);
        barSatHisCRISTC.addSeries(med_satisfecho);
        barSatHisCRISTC.addSeries(no_satisfecho);
        return barSatHisCRISTC;
    }      
    
   public BarChartModel obtenerSatHistoricoBarSANJOS() {
        
        barSatHisSANJOS = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisSANJOS = resDimensionService.sp_mc_general_historico("SANJOS");
            System.out.println("Lista  de historico =====>" + listaSatHisSANJOS.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisSANJOS.isEmpty()) {
                for (HistoricoBean satisfaccionHisSANJOS : listaSatHisSANJOS) {
                    res = Math.round(satisfaccionHisSANJOS.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisSANJOS.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisSANJOS.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisSANJOS.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisSANJOS.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisSANJOS.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisSANJOS.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisSANJOS.getPorSatisfecho().floatValue())));    
                        satisfaccionHisSANJOS.setPorMedSatisfecho(newval);
                    }                     
                    no_satisfecho.set(satisfaccionHisSANJOS.getAnio(), satisfaccionHisSANJOS.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisSANJOS.getAnio(), satisfaccionHisSANJOS.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisSANJOS.getAnio(), satisfaccionHisSANJOS.getPorSatisfecho().floatValue());
                }
                barSatHisSANJOS.setTitle("San Jose Obrero de Sullana");
                barSatHisSANJOS.setLegendPosition("ne");
                barSatHisSANJOS.setStacked(true);
                barSatHisSANJOS.setShowPointLabels(true);
                barSatHisSANJOS.setAnimate(true);
                barSatHisSANJOS.setExtender("chartBarHistoricoExtender");
                barSatHisSANJOS.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisSANJOS.addSeries(satisfecho);
        barSatHisSANJOS.addSeries(med_satisfecho);
        barSatHisSANJOS.addSeries(no_satisfecho);
        return barSatHisSANJOS;
    }     
   
   public BarChartModel obtenerSatHistoricoBarSTAMAR() {
        
        barSatHisSTAMAR = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisSTAMAR = resDimensionService.sp_mc_general_historico("STAMAR");
            System.out.println("Lista  de historico =====>" + listaSatHisSTAMAR.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisSTAMAR.isEmpty()) {
                for (HistoricoBean satisfaccionHisSTAMAR : listaSatHisSTAMAR) {
                    res = Math.round(satisfaccionHisSTAMAR.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisSTAMAR.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisSTAMAR.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisSTAMAR.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisSTAMAR.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisSTAMAR.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisSTAMAR.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisSTAMAR.getPorSatisfecho().floatValue())));    
                        satisfaccionHisSTAMAR.setPorMedSatisfecho(newval);
                    }                     
                    no_satisfecho.set(satisfaccionHisSTAMAR.getAnio(), satisfaccionHisSTAMAR.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisSTAMAR.getAnio(), satisfaccionHisSTAMAR.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisSTAMAR.getAnio(), satisfaccionHisSTAMAR.getPorSatisfecho().floatValue());
                }
                barSatHisSTAMAR.setTitle("Santa Maria de los Andes");
                barSatHisSTAMAR.setLegendPosition("ne");
                barSatHisSTAMAR.setStacked(true);
                barSatHisSTAMAR.setShowPointLabels(true);
                barSatHisSTAMAR.setAnimate(true);
                barSatHisSTAMAR.setExtender("chartBarHistoricoExtender");
                barSatHisSTAMAR.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisSTAMAR.addSeries(satisfecho);
        barSatHisSTAMAR.addSeries(med_satisfecho);
        barSatHisSTAMAR.addSeries(no_satisfecho);
        return barSatHisSTAMAR;
    }  
    
   public BarChartModel obtenerSatHistoricoBarSTAROS() {
        
        barSatHisSTAROS= new BarChartModel();
        
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisSTAROS = resDimensionService.sp_mc_general_historico("STAROS");
            System.out.println("Lista  de historico =====>" + listaSatHisSTAROS.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisSTAROS.isEmpty()) {
                for (HistoricoBean satisfaccionHisSTAROS : listaSatHisSTAROS) {
                    res = Math.round(satisfaccionHisSTAROS.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisSTAROS.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisSTAROS.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisSTAROS.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisSTAROS.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisSTAROS.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisSTAROS.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisSTAROS.getPorSatisfecho().floatValue())));    
                        satisfaccionHisSTAROS.setPorMedSatisfecho(newval);
                    }                      
                    no_satisfecho.set(satisfaccionHisSTAROS.getAnio(), satisfaccionHisSTAROS.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisSTAROS.getAnio(), satisfaccionHisSTAROS.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisSTAROS.getAnio(), satisfaccionHisSTAROS.getPorSatisfecho().floatValue());
                }
                barSatHisSTAROS.setTitle("Colegio Santa Rosa de Sullana");
                barSatHisSTAROS.setLegendPosition("ne");
                barSatHisSTAROS.setStacked(true);
                barSatHisSTAROS.setShowPointLabels(true);
                barSatHisSTAROS.setAnimate(true);
                barSatHisSTAROS.setExtender("chartBarHistoricoExtender");
                barSatHisSTAROS.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisSTAROS.addSeries(satisfecho);
        barSatHisSTAROS.addSeries(med_satisfecho);
        barSatHisSTAROS.addSeries(no_satisfecho);
        return barSatHisSTAROS;
    }        
        
    public BarChartModel obtenerSatHistoricoBarSANJOC() {
        
        barSatHisSANJOC = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisSANJOC = resDimensionService.sp_mc_general_historico("SANJOC");
            System.out.println("Lista  de historico =====>" + listaSatHisSANJOC.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisSANJOC.isEmpty()) {
                for (HistoricoBean satisfaccionHisSANJOC : listaSatHisSANJOC) {
                    res = Math.round(satisfaccionHisSANJOC.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisSANJOC.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisSANJOC.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisSANJOC.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisSANJOC.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisSANJOC.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisSANJOC.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisSANJOC.getPorSatisfecho().floatValue())));    
                        satisfaccionHisSANJOC.setPorMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(satisfaccionHisSANJOC.getAnio(), satisfaccionHisSANJOC.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisSANJOC.getAnio(), satisfaccionHisSANJOC.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisSANJOC.getAnio(), satisfaccionHisSANJOC.getPorSatisfecho().floatValue());
                }
                barSatHisSANJOC.setTitle("San Jose del Callao");
                barSatHisSANJOC.setLegendPosition("ne");
                barSatHisSANJOC.setStacked(true);
                barSatHisSANJOC.setShowPointLabels(true);
                barSatHisSANJOC.setAnimate(true);
                barSatHisSANJOC.setExtender("chartBarHistoricoExtender");
                barSatHisSANJOC.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisSANJOC.addSeries(satisfecho);
        barSatHisSANJOC.addSeries(med_satisfecho);
        barSatHisSANJOC.addSeries(no_satisfecho);
        return barSatHisSANJOC;
    }      
    
    
    public BarChartModel obtenerSatHistoricoBarSANJOH() {
        
        barSatHisSANJOH = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisSANJOH = resDimensionService.sp_mc_general_historico("SANJOH");
            System.out.println("Lista  de historico =====>" + listaSatHisSANJOH.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisSANJOH.isEmpty()) {
                for (HistoricoBean satisfaccionHisSANJOH : listaSatHisSANJOH) {
                    res = Math.round(satisfaccionHisSANJOH.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisSANJOH.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisSANJOH.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisSANJOH.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisSANJOH.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisSANJOH.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisSANJOH.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisSANJOH.getPorSatisfecho().floatValue())));    
                        satisfaccionHisSANJOH.setPorMedSatisfecho(newval);
                    }                     
                    no_satisfecho.set(satisfaccionHisSANJOH.getAnio(), satisfaccionHisSANJOH.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisSANJOH.getAnio(), satisfaccionHisSANJOH.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisSANJOH.getAnio(), satisfaccionHisSANJOH.getPorSatisfecho().floatValue());
                }
                barSatHisSANJOH.setTitle("Colegio San Jose de Huacho");
                barSatHisSANJOH.setLegendPosition("ne");
                barSatHisSANJOH.setStacked(true);
                barSatHisSANJOH.setShowPointLabels(true);
                barSatHisSANJOH.setAnimate(true);
                barSatHisSANJOH.setExtender("chartBarHistoricoExtender");
                barSatHisSANJOH.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisSANJOH.addSeries(satisfecho);
        barSatHisSANJOH.addSeries(med_satisfecho);
        barSatHisSANJOH.addSeries(no_satisfecho);
        return barSatHisSANJOH;
    }    
    

   public BarChartModel obtenerSatHistoricoBarUMCH() {
        
        barSatHisUMCH= new BarChartModel();
        
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatHisUMCH = resDimensionService.sp_mc_general_historico("UMCH");
            System.out.println("Lista  de historico =====>" + listaSatHisUMCH.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatHisUMCH.isEmpty()) {
                for (HistoricoBean satisfaccionHisUMCH : listaSatHisUMCH) {
                    res = Math.round(satisfaccionHisUMCH.getPorNoSatisfecho().floatValue())  + 
						  Math.round(satisfaccionHisUMCH.getPorMedSatisfecho().floatValue()) +  
						  Math.round(satisfaccionHisUMCH.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(satisfaccionHisUMCH.getPorNoSatisfecho().floatValue()) + 
						Math.round(satisfaccionHisUMCH.getPorMedSatisfecho().floatValue()))  ; 
                        satisfaccionHisUMCH.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(satisfaccionHisUMCH.getPorNoSatisfecho().floatValue()) +
						Math.round(satisfaccionHisUMCH.getPorSatisfecho().floatValue())));    
                        satisfaccionHisUMCH.setPorMedSatisfecho(newval);
                    }                      
                    no_satisfecho.set(satisfaccionHisUMCH.getAnio(), satisfaccionHisUMCH.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(satisfaccionHisUMCH.getAnio(), satisfaccionHisUMCH.getPorMedSatisfecho().floatValue());
                    satisfecho.set(satisfaccionHisUMCH.getAnio(), satisfaccionHisUMCH.getPorSatisfecho().floatValue());
                }
                barSatHisUMCH.setTitle("Universidad Champagnat");
                barSatHisUMCH.setLegendPosition("ne");
                barSatHisUMCH.setStacked(true);
                barSatHisUMCH.setShowPointLabels(true);
                barSatHisUMCH.setAnimate(true);
                barSatHisUMCH.setExtender("chartBarHistoricoExtender");
                barSatHisUMCH.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatHisUMCH.addSeries(satisfecho);
        barSatHisUMCH.addSeries(med_satisfecho);
        barSatHisUMCH.addSeries(no_satisfecho);
        return barSatHisUMCH;
    } 
/*******************************/    
    public BarChartModel obtenerResDimensionSecPeruBar() {
        barResDimensionSecPeru = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaResDimensionSecPerubar = resDimensionService.sp_mc_resultadoxdimensiones("ALL",anio);
            /*String  val_no_satisfecho;*/
            if (!listaResDimensionSecPerubar.isEmpty()) {
                for (ResDimensionBean resDimensionSecPerubar : listaResDimensionSecPerubar) {
                    res = Math.round(resDimensionSecPerubar.getNoSatisfecho().floatValue())  + 
						  Math.round(resDimensionSecPerubar.getMedSatisfecho().floatValue()) +  
						  Math.round(resDimensionSecPerubar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resDimensionSecPerubar.getNoSatisfecho().floatValue()) + 
						Math.round(resDimensionSecPerubar.getMedSatisfecho().floatValue()))  ; 
                        resDimensionSecPerubar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resDimensionSecPerubar.getNoSatisfecho().floatValue()) +
						Math.round(resDimensionSecPerubar.getSatisfecho().floatValue())));    
                        resDimensionSecPerubar.setMedSatisfecho(newval);
                    } 
                    no_satisfecho.set(resDimensionSecPerubar.getIdTipoDimension(), resDimensionSecPerubar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resDimensionSecPerubar.getIdTipoDimension(), resDimensionSecPerubar.getMedSatisfecho().floatValue());
                    satisfecho.set(resDimensionSecPerubar.getIdTipoDimension(), resDimensionSecPerubar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barResDimensionSecPeru.setTitle("Historico");
                barResDimensionSecPeru.setLegendPosition("ne");
                /*barResDimension.setShowDatatip(true);   */
                barResDimensionSecPeru.setShowPointLabels(true);
                barResDimensionSecPeru.setAnimate(true);
                barResDimensionSecPeru.setExtender("chartBarExtender"); //
                barResDimensionSecPeru.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barResDimensionSecPeru.addSeries(satisfecho);
        barResDimensionSecPeru.addSeries(med_satisfecho);
        barResDimensionSecPeru.addSeries(no_satisfecho);
        return barResDimensionSecPeru;
    }    
    
    public BarChartModel obtenerResDimensionSecPeruBar_est_part(String tip_colegio) {
        barResDimensionSecPeru_est_part = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaResDimensionSecPerubar_est_part = resDimensionService.sp_mc_resultadoxdimensiones_estatal_particular(tip_colegio,anio);
            /*String  val_no_satisfecho;*/
            if (!listaResDimensionSecPerubar_est_part.isEmpty()) {
                for (ResDimensionBean resDimensionSecPerubar_est_part : listaResDimensionSecPerubar_est_part) {
                    res = Math.round(resDimensionSecPerubar_est_part.getNoSatisfecho().floatValue())  + 
						  Math.round(resDimensionSecPerubar_est_part.getMedSatisfecho().floatValue()) +  
						  Math.round(resDimensionSecPerubar_est_part.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resDimensionSecPerubar_est_part.getNoSatisfecho().floatValue()) + 
						Math.round(resDimensionSecPerubar_est_part.getMedSatisfecho().floatValue()))  ; 
                        resDimensionSecPerubar_est_part.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resDimensionSecPerubar_est_part.getNoSatisfecho().floatValue()) +
						Math.round(resDimensionSecPerubar_est_part.getSatisfecho().floatValue())));    
                        resDimensionSecPerubar_est_part.setMedSatisfecho(newval);
                    } 
                    no_satisfecho.set(resDimensionSecPerubar_est_part.getIdTipoDimension(), resDimensionSecPerubar_est_part.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resDimensionSecPerubar_est_part.getIdTipoDimension(), resDimensionSecPerubar_est_part.getMedSatisfecho().floatValue());
                    satisfecho.set(resDimensionSecPerubar_est_part.getIdTipoDimension(), resDimensionSecPerubar_est_part.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barResDimensionSecPeru_est_part.setTitle("Historico");
                barResDimensionSecPeru_est_part.setLegendPosition("ne");
                /*barResDimension.setShowDatatip(true);   */
                barResDimensionSecPeru_est_part.setShowPointLabels(true);
                barResDimensionSecPeru_est_part.setAnimate(true);
                barResDimensionSecPeru_est_part.setExtender("chartBarExtender"); //
                barResDimensionSecPeru_est_part.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barResDimensionSecPeru_est_part.addSeries(satisfecho);
        barResDimensionSecPeru_est_part.addSeries(med_satisfecho);
        barResDimensionSecPeru_est_part.addSeries(no_satisfecho);
        return barResDimensionSecPeru_est_part;
    }    

    
    public PieChartModel obtenerSatisfaccionConGeneralPie() {
        pieSatisfaccionGeneral = new PieChartModel();
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaSatisfaccionGeneral = resDimensionService.sp_mc_pie_satisfaccion_general("ALL",anio);
            System.out.println("lista pie directiva  ==> " + listaSatisfaccionGeneral.size());
            if (!listaSatisfaccionGeneral.isEmpty()) {
                
                for (GrPieChartBean pieSatisfaccionConGralPieres : listaSatisfaccionGeneral) {
                   res += Math.round(pieSatisfaccionConGralPieres.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieSatisfaccionConGralPie : listaSatisfaccionGeneral) {
                    val = Math.round(pieSatisfaccionConGralPie.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieSatisfaccionConGralPie.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieSatisfaccionConGralPie.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                       newval = val;
                    }                    
                    pieSatisfaccionGeneral.set(pieSatisfaccionConGralPie.getDescripcion(),newval);
                }
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieSatisfaccionGeneral.setTitle("Satisfaccion General");
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


    public List<ConGeneralBean> getListaGeneralUninegA() {
        if (listaGeneralUninegA == null) {
            listaGeneralUninegA = new ArrayList<>();
        }          
        return listaGeneralUninegA;
    }

    public void setListaGeneralUninegA(List<ConGeneralBean> listaGeneralUninegA) {
        this.listaGeneralUninegA = listaGeneralUninegA;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegA() {
        if (listaGrpGeneralUninegA == null) {
            listaGrpGeneralUninegA = new ArrayList<>();
        }                 
        return listaGrpGeneralUninegA;
    }

    public void setListaGrpGeneralUninegA(List<ConGeneralBean> listaGrpGeneralUninegA) {
        this.listaGrpGeneralUninegA = listaGrpGeneralUninegA;
    }

    public BarChartModel getBarGeneralUninegA() {
        return barGeneralUninegA;
    }

    public void setBarGeneralUninegA(BarChartModel barGeneralUninegA) {
        this.barGeneralUninegA = barGeneralUninegA;
    }

    public BarChartModel getBarGrpGeneralUninegA() {
        return barGrpGeneralUninegA;
    }

    public void setBarGrpGeneralUninegA(BarChartModel barGrpGeneralUninegA) {
        this.barGrpGeneralUninegA = barGrpGeneralUninegA;
    }

    public List<ConGeneralBean> getListaGeneralUninegB() {
        if (listaGeneralUninegB == null) {
            listaGeneralUninegB = new ArrayList<>();
        }         
        return listaGeneralUninegB;
    }

    public void setListaGeneralUninegB(List<ConGeneralBean> listaGeneralUninegB) {
        this.listaGeneralUninegB = listaGeneralUninegB;
    }

    public List<ConGeneralBean> getListaGeneralUninegC() {
        if (listaGeneralUninegC == null) {
            listaGeneralUninegC = new ArrayList<>();
        }          
        return listaGeneralUninegC;
    }

    public void setListaGeneralUninegC(List<ConGeneralBean> listaGeneralUninegC) {
        this.listaGeneralUninegC = listaGeneralUninegC;
    }

    public List<ConGeneralBean> getListaGeneralUninegD() {
        if (listaGeneralUninegD == null) {
            listaGeneralUninegD = new ArrayList<>();
        }            
        return listaGeneralUninegD;
    }

    public void setListaGeneralUninegD(List<ConGeneralBean> listaGeneralUninegD) {
        this.listaGeneralUninegD = listaGeneralUninegD;
    }

    public List<ConGeneralBean> getListaGeneralUninegE() {
        if (listaGeneralUninegE == null) {
            listaGeneralUninegE = new ArrayList<>();
        }            
        return listaGeneralUninegE;
    }

    public void setListaGeneralUninegE(List<ConGeneralBean> listaGeneralUninegE) {
        this.listaGeneralUninegE = listaGeneralUninegE;
    }

    public List<ConGeneralBean> getListaGeneralUninegF() {
        if (listaGeneralUninegF == null) {
            listaGeneralUninegF = new ArrayList<>();
        }            
        return listaGeneralUninegF;
    }

    public void setListaGeneralUninegF(List<ConGeneralBean> listaGeneralUninegF) {
        this.listaGeneralUninegF = listaGeneralUninegF;
    }

    public List<ConGeneralBean> getListaGeneralUninegG() {
        if (listaGeneralUninegG == null) {
            listaGeneralUninegG = new ArrayList<>();
        }            
        return listaGeneralUninegG;
    }

    public void setListaGeneralUninegG(List<ConGeneralBean> listaGeneralUninegG) {
        this.listaGeneralUninegG = listaGeneralUninegG;
    }

    public List<ConGeneralBean> getListaGeneralUninegH() {
        if (listaGeneralUninegH == null) {
            listaGeneralUninegH = new ArrayList<>();
        }            
        return listaGeneralUninegH;
    }

    public void setListaGeneralUninegH(List<ConGeneralBean> listaGeneralUninegH) {
        this.listaGeneralUninegH = listaGeneralUninegH;
    }

    public List<ConGeneralBean> getListaGeneralUninegI() {
        if (listaGeneralUninegI == null) {
            listaGeneralUninegI = new ArrayList<>();
        }            
        return listaGeneralUninegI;
    }

    public void setListaGeneralUninegI(List<ConGeneralBean> listaGeneralUninegI) {
        this.listaGeneralUninegI = listaGeneralUninegI;
    }

    public List<ConGeneralBean> getListaGeneralUninegJ() {
        if (listaGeneralUninegJ == null) {
            listaGeneralUninegJ = new ArrayList<>();
        }            
        return listaGeneralUninegJ;
    }

    public void setListaGeneralUninegJ(List<ConGeneralBean> listaGeneralUninegJ) {
        this.listaGeneralUninegJ = listaGeneralUninegJ;
    }

    public List<ConGeneralBean> getListaGeneralUninegK() {
        if (listaGeneralUninegK == null) {
            listaGeneralUninegK = new ArrayList<>();
        }            
        return listaGeneralUninegK;
    }

    public void setListaGeneralUninegK(List<ConGeneralBean> listaGeneralUninegK) {
        this.listaGeneralUninegK = listaGeneralUninegK;
    }

    public List<ConGeneralBean> getListaGeneralUninegL() {
        if (listaGeneralUninegL == null) {
            listaGeneralUninegL = new ArrayList<>();
        }            
        return listaGeneralUninegL;
    }

    public void setListaGeneralUninegL(List<ConGeneralBean> listaGeneralUninegL) {
        this.listaGeneralUninegL = listaGeneralUninegL;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegB() {
        return listaGrpGeneralUninegB;
    }

    public void setListaGrpGeneralUninegB(List<ConGeneralBean> listaGrpGeneralUninegB) {
        this.listaGrpGeneralUninegB = listaGrpGeneralUninegB;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegC() {
        return listaGrpGeneralUninegC;
    }

    public void setListaGrpGeneralUninegC(List<ConGeneralBean> listaGrpGeneralUninegC) {
        this.listaGrpGeneralUninegC = listaGrpGeneralUninegC;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegD() {
        return listaGrpGeneralUninegD;
    }

    public void setListaGrpGeneralUninegD(List<ConGeneralBean> listaGrpGeneralUninegD) {
        this.listaGrpGeneralUninegD = listaGrpGeneralUninegD;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegE() {
        return listaGrpGeneralUninegE;
    }

    public void setListaGrpGeneralUninegE(List<ConGeneralBean> listaGrpGeneralUninegE) {
        this.listaGrpGeneralUninegE = listaGrpGeneralUninegE;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegF() {
        return listaGrpGeneralUninegF;
    }

    public void setListaGrpGeneralUninegF(List<ConGeneralBean> listaGrpGeneralUninegF) {
        this.listaGrpGeneralUninegF = listaGrpGeneralUninegF;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegG() {
        return listaGrpGeneralUninegG;
    }

    public void setListaGrpGeneralUninegG(List<ConGeneralBean> listaGrpGeneralUninegG) {
        this.listaGrpGeneralUninegG = listaGrpGeneralUninegG;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegH() {
        return listaGrpGeneralUninegH;
    }

    public void setListaGrpGeneralUninegH(List<ConGeneralBean> listaGrpGeneralUninegH) {
        this.listaGrpGeneralUninegH = listaGrpGeneralUninegH;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegI() {
        return listaGrpGeneralUninegI;
    }

    public void setListaGrpGeneralUninegI(List<ConGeneralBean> listaGrpGeneralUninegI) {
        this.listaGrpGeneralUninegI = listaGrpGeneralUninegI;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegJ() {
        return listaGrpGeneralUninegJ;
    }

    public void setListaGrpGeneralUninegJ(List<ConGeneralBean> listaGrpGeneralUninegJ) {
        this.listaGrpGeneralUninegJ = listaGrpGeneralUninegJ;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegK() {
        return listaGrpGeneralUninegK;
    }

    public void setListaGrpGeneralUninegK(List<ConGeneralBean> listaGrpGeneralUninegK) {
        this.listaGrpGeneralUninegK = listaGrpGeneralUninegK;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegL() {
        return listaGrpGeneralUninegL;
    }

    public void setListaGrpGeneralUninegL(List<ConGeneralBean> listaGrpGeneralUninegL) {
        this.listaGrpGeneralUninegL = listaGrpGeneralUninegL;
    }

    public BarChartModel getBarGeneralUninegB() {
        return barGeneralUninegB;
    }

    public void setBarGeneralUninegB(BarChartModel barGeneralUninegB) {
        this.barGeneralUninegB = barGeneralUninegB;
    }

    public BarChartModel getBarGeneralUninegC() {
        return barGeneralUninegC;
    }

    public void setBarGeneralUninegC(BarChartModel barGeneralUninegC) {
        this.barGeneralUninegC = barGeneralUninegC;
    }

    public BarChartModel getBarGeneralUninegD() {
        return barGeneralUninegD;
    }

    public void setBarGeneralUninegD(BarChartModel barGeneralUninegD) {
        this.barGeneralUninegD = barGeneralUninegD;
    }

    public BarChartModel getBarGeneralUninegE() {
        return barGeneralUninegE;
    }

    public void setBarGeneralUninegE(BarChartModel barGeneralUninegE) {
        this.barGeneralUninegE = barGeneralUninegE;
    }

    public BarChartModel getBarGeneralUninegF() {
        return barGeneralUninegF;
    }

    public void setBarGeneralUninegF(BarChartModel barGeneralUninegF) {
        this.barGeneralUninegF = barGeneralUninegF;
    }

    public BarChartModel getBarGeneralUninegH() {
        return barGeneralUninegH;
    }

    public void setBarGeneralUninegH(BarChartModel barGeneralUninegH) {
        this.barGeneralUninegH = barGeneralUninegH;
    }

    public BarChartModel getBarGeneralUninegI() {
        return barGeneralUninegI;
    }

    public void setBarGeneralUninegI(BarChartModel barGeneralUninegI) {
        this.barGeneralUninegI = barGeneralUninegI;
    }

    public BarChartModel getBarGeneralUninegJ() {
        return barGeneralUninegJ;
    }

    public void setBarGeneralUninegJ(BarChartModel barGeneralUninegJ) {
        this.barGeneralUninegJ = barGeneralUninegJ;
    }

    public BarChartModel getBarGeneralUninegK() {
        return barGeneralUninegK;
    }

    public void setBarGeneralUninegK(BarChartModel barGeneralUninegK) {
        this.barGeneralUninegK = barGeneralUninegK;
    }

    public BarChartModel getBarGeneralUninegL() {
        return barGeneralUninegL;
    }

    public void setBarGeneralUninegL(BarChartModel barGeneralUninegL) {
        this.barGeneralUninegL = barGeneralUninegL;
    }

    public BarChartModel getBarGrpGeneralUninegB() {
        return barGrpGeneralUninegB;
    }

    public void setBarGrpGeneralUninegB(BarChartModel barGrpGeneralUninegB) {
        this.barGrpGeneralUninegB = barGrpGeneralUninegB;
    }

    public BarChartModel getBarGrpGeneralUninegC() {
        return barGrpGeneralUninegC;
    }

    public void setBarGrpGeneralUninegC(BarChartModel barGrpGeneralUninegC) {
        this.barGrpGeneralUninegC = barGrpGeneralUninegC;
    }

    public BarChartModel getBarGrpGeneralUninegD() {
        return barGrpGeneralUninegD;
    }

    public void setBarGrpGeneralUninegD(BarChartModel barGrpGeneralUninegD) {
        this.barGrpGeneralUninegD = barGrpGeneralUninegD;
    }

    public BarChartModel getBarGrpGeneralUninegE() {
        return barGrpGeneralUninegE;
    }

    public void setBarGrpGeneralUninegE(BarChartModel barGrpGeneralUninegE) {
        this.barGrpGeneralUninegE = barGrpGeneralUninegE;
    }

    public BarChartModel getBarGrpGeneralUninegF() {
        return barGrpGeneralUninegF;
    }

    public void setBarGrpGeneralUninegF(BarChartModel barGrpGeneralUninegF) {
        this.barGrpGeneralUninegF = barGrpGeneralUninegF;
    }

    public BarChartModel getBarGrpGeneralUninegG() {
        return barGrpGeneralUninegG;
    }

    public void setBarGrpGeneralUninegG(BarChartModel barGrpGeneralUninegG) {
        this.barGrpGeneralUninegG = barGrpGeneralUninegG;
    }

    public BarChartModel getBarGrpGeneralUninegH() {
        return barGrpGeneralUninegH;
    }

    public void setBarGrpGeneralUninegH(BarChartModel barGrpGeneralUninegH) {
        this.barGrpGeneralUninegH = barGrpGeneralUninegH;
    }

    public BarChartModel getBarGrpGeneralUninegI() {
        return barGrpGeneralUninegI;
    }

    public void setBarGrpGeneralUninegI(BarChartModel barGrpGeneralUninegI) {
        this.barGrpGeneralUninegI = barGrpGeneralUninegI;
    }

    public BarChartModel getBarGrpGeneralUninegJ() {
        return barGrpGeneralUninegJ;
    }

    public void setBarGrpGeneralUninegJ(BarChartModel barGrpGeneralUninegJ) {
        this.barGrpGeneralUninegJ = barGrpGeneralUninegJ;
    }

    public BarChartModel getBarGrpGeneralUninegK() {
        return barGrpGeneralUninegK;
    }

    public void setBarGrpGeneralUninegK(BarChartModel barGrpGeneralUninegK) {
        this.barGrpGeneralUninegK = barGrpGeneralUninegK;
    }

    public BarChartModel getBarGrpGeneralUninegL() {
        return barGrpGeneralUninegL;
    }

    public void setBarGrpGeneralUninegL(BarChartModel barGrpGeneralUninegL) {
        this.barGrpGeneralUninegL = barGrpGeneralUninegL;
    }

    public BarChartModel getBarGeneralUninegG() {
        return barGeneralUninegG;
    }

    public void setBarGeneralUninegG(BarChartModel barGeneralUninegG) {
        this.barGeneralUninegG = barGeneralUninegG;
    }
	    public BarChartModel getBarSatisfaccionGral() {
        return barSatisfaccionGral;
    }

    public void setBarSatisfaccionGral(BarChartModel barSatisfaccionGral) {
        this.barSatisfaccionGral = barSatisfaccionGral;
    }

    public List<SatisfaccionGrlBean> getListaSatisfaccionGral() {
        if (listaSatisfaccionGral == null) {
            listaSatisfaccionGral = new ArrayList<>();
        }           
        return listaSatisfaccionGral;
    }

    public void setListaSatisfaccionGral(List<SatisfaccionGrlBean> listaSatisfaccionGral) {
        this.listaSatisfaccionGral = listaSatisfaccionGral;
    }

    public BarChartModel getBarSatisfaccionGralHis() {
        return barSatisfaccionGralHis;
    }

    public void setBarSatisfaccionGralHis(BarChartModel barSatisfaccionGralHis) {
        this.barSatisfaccionGralHis = barSatisfaccionGralHis;
    }

    public List<SatisfaccionGrlBean> getListaSatisfaccionGralHis() {
        if (listaSatisfaccionGralHis == null) {
            listaSatisfaccionGralHis = new ArrayList<>();
        }             
        return listaSatisfaccionGralHis;
    }

    public void setListaSatisfaccionGralHis(List<SatisfaccionGrlBean> listaSatisfaccionGralHis) {
        this.listaSatisfaccionGralHis = listaSatisfaccionGralHis;
    }

    public BarChartModel getBarSatHisSANLUI() {
        return barSatHisSANLUI;
    }

    public void setBarSatHisSANLUI(BarChartModel barSatHisSANLUI) {
        this.barSatHisSANLUI = barSatHisSANLUI;
    }

    public BarChartModel getBarSatHisBARINA() {
        return barSatHisBARINA;
    }

    public void setBarSatHisBARINA(BarChartModel barSatHisBARINA) {
        this.barSatHisBARINA = barSatHisBARINA;
    }

    public List<HistoricoBean> getListaSatHisSANLUI() {
        if (listaSatHisSANLUI == null) {
            listaSatHisSANLUI = new ArrayList<>();
        }           
        return listaSatHisSANLUI;
    }

    public void setListaSatHisSANLUI(List<HistoricoBean> listaSatHisSANLUI) {
        this.listaSatHisSANLUI = listaSatHisSANLUI;
    }

    public List<HistoricoBean> getListaSatHisBARINA() {
        if (listaSatHisBARINA == null) {
            listaSatHisBARINA = new ArrayList<>();
        }              
        return listaSatHisBARINA;
    }

    public void setListaSatHisBARINA(List<HistoricoBean> listaSatHisBARINA) {
        this.listaSatHisBARINA = listaSatHisBARINA;
    }

    public BarChartModel getBarSatHisCHAMPC() {
        return barSatHisCHAMPC;
    }

    public void setBarSatHisCHAMPC(BarChartModel barSatHisCHAMPC) {
        this.barSatHisCHAMPC = barSatHisCHAMPC;
    }

    public BarChartModel getBarSatHisCHAMPS() {
        return barSatHisCHAMPS;
    }

    public void setBarSatHisCHAMPS(BarChartModel barSatHisCHAMPS) {
        this.barSatHisCHAMPS = barSatHisCHAMPS;
    }

    public BarChartModel getBarSatHisSTAROS() {
        return barSatHisSTAROS;
    }

    public void setBarSatHisSTAROS(BarChartModel barSatHisSTAROS) {
        this.barSatHisSTAROS = barSatHisSTAROS;
    }

    public BarChartModel getBarSatHisSANJOC() {
        return barSatHisSANJOC;
    }

    public void setBarSatHisSANJOC(BarChartModel barSatHisSANJOC) {
        this.barSatHisSANJOC = barSatHisSANJOC;
    }

    public BarChartModel getBarSatHisSANJOH() {
        return barSatHisSANJOH;
    }

    public void setBarSatHisSANJOH(BarChartModel barSatHisSANJOH) {
        this.barSatHisSANJOH = barSatHisSANJOH;
    }

    public List<HistoricoBean> getListaSatHisCHAMPC() {
        if (listaSatHisCHAMPC == null) {
            listaSatHisCHAMPC = new ArrayList<>();
        }         
        return listaSatHisCHAMPC;
    }

    public void setListaSatHisCHAMPC(List<HistoricoBean> listaSatHisCHAMPC) {
        this.listaSatHisCHAMPC = listaSatHisCHAMPC;
    }

    public List<HistoricoBean> getListaSatHisCHAMPS() {
        if (listaSatHisCHAMPS == null) {
            listaSatHisCHAMPS = new ArrayList<>();
        }         
        return listaSatHisCHAMPS;
    }

    public void setListaSatHisCHAMPS(List<HistoricoBean> listaSatHisCHAMPS) {
        this.listaSatHisCHAMPS = listaSatHisCHAMPS;
    }

    public List<HistoricoBean> getListaSatHisSTAROS() {
        if (listaSatHisSTAROS == null) {
            listaSatHisSTAROS = new ArrayList<>();
        }         
        return listaSatHisSTAROS;
    }

    public void setListaSatHisSTAROS(List<HistoricoBean> listaSatHisSTAROS) {
        this.listaSatHisSTAROS = listaSatHisSTAROS;
    }

    public List<HistoricoBean> getListaSatHisSANJOC() {
        if (listaSatHisSANJOC == null) {
            listaSatHisSANJOC = new ArrayList<>();
        }         
        return listaSatHisSANJOC;
    }

    public void setListaSatHisSANJOC(List<HistoricoBean> listaSatHisSANJOC) {
        this.listaSatHisSANJOC = listaSatHisSANJOC;
    }

    public List<HistoricoBean> getListaSatHisSANJOH() {
        if (listaSatHisSANJOH == null) {
            listaSatHisSANJOH = new ArrayList<>();
        }         
        return listaSatHisSANJOH;
    }

    public void setListaSatHisSANJOH(List<HistoricoBean> listaSatHisSANJOH) {
        this.listaSatHisSANJOH = listaSatHisSANJOH;
    }

    public BarChartModel getBarResDimensionSecPeru() {
        return barResDimensionSecPeru;
    }

    public void setBarResDimensionSecPeru(BarChartModel barResDimensionSecPeru) {
        this.barResDimensionSecPeru = barResDimensionSecPeru;
    }

    public List<ResDimensionBean> getListaResDimensionSecPerubar() {
        if (listaResDimensionSecPerubar == null) {
            listaResDimensionSecPerubar = new ArrayList<>();
        }        
        return listaResDimensionSecPerubar;
    }

    public void setListaResDimensionSecPerubar(List<ResDimensionBean> listaResDimensionSecPerubar) {
        this.listaResDimensionSecPerubar = listaResDimensionSecPerubar;
    }

    public List<SugerenciasBean> getListaSugerencias() {
        if (listaSugerencias == null) {
            listaSugerencias = new ArrayList<>();
        }         
        return listaSugerencias;
    }

    public void setListaSugerencias(List<SugerenciasBean> listaSugerencias) {
        this.listaSugerencias = listaSugerencias;
    }

    public PieChartModel getPieSatisfaccionGeneral() {
        return pieSatisfaccionGeneral;
    }

    public void setPieSatisfaccionGeneral(PieChartModel pieSatisfaccionGeneral) {
        this.pieSatisfaccionGeneral = pieSatisfaccionGeneral;
    }

    public List<GrPieChartBean> getListaSatisfaccionGeneral() {
        if (listaSatisfaccionGeneral == null) {
            listaSatisfaccionGeneral = new ArrayList<>();
        }         
        return listaSatisfaccionGeneral;
    }

    public void setListaSatisfaccionGeneral(List<GrPieChartBean> listaSatisfaccionGeneral) {
        this.listaSatisfaccionGeneral = listaSatisfaccionGeneral;
    }

    public List<CantidadBean> getListaCantConGeneral() {
        if (listaCantConGeneral == null) {
            listaCantConGeneral = new ArrayList<>();
        }         
        return listaCantConGeneral;
    }

    public void setListaCantConGeneral(List<CantidadBean> listaCantConGeneral) {
        this.listaCantConGeneral = listaCantConGeneral;
    }

    public List<PoblacionCons> getListaCantConsUnineg() {
        if (listaCantConsUnineg == null) {
            listaCantConsUnineg = new ArrayList<>();
        }         
        return listaCantConsUnineg;
    }

    public void setListaCantConsUnineg(List<PoblacionCons> listaCantConsUnineg) {
        this.listaCantConsUnineg = listaCantConsUnineg;
    }

/*************************************************************************************************************/
    

    public List<PoblacionCons> getListaCantConsDimen_A() {
        if (listaCantConsDimen_A == null) {
            listaCantConsDimen_A = new ArrayList<>();
        }           
        return listaCantConsDimen_A;
    }

    public void setListaCantConsDimen_A(List<PoblacionCons> listaCantConsDimen_A) {
        this.listaCantConsDimen_A = listaCantConsDimen_A;
    }

    public List<PoblacionCons> getListaCantConsDimen_B() {
        if (listaCantConsDimen_B == null) {
            listaCantConsDimen_B = new ArrayList<>();
        }           
        return listaCantConsDimen_B;
    }

    public void setListaCantConsDimen_B(List<PoblacionCons> listaCantConsDimen_B) {
        this.listaCantConsDimen_B = listaCantConsDimen_B;
    }

    public List<PoblacionCons> getListaCantConsDimen_C() {
        if (listaCantConsDimen_C == null) {
            listaCantConsDimen_C = new ArrayList<>();
        }           
        return listaCantConsDimen_C;
    }

    public void setListaCantConsDimen_C(List<PoblacionCons> listaCantConsDimen_C) {
        this.listaCantConsDimen_C = listaCantConsDimen_C;
    }

    public List<PoblacionCons> getListaCantConsDimen_D() {
        if (listaCantConsDimen_D == null) {
            listaCantConsDimen_D = new ArrayList<>();
        }           
        return listaCantConsDimen_D;
    }

    public void setListaCantConsDimen_D(List<PoblacionCons> listaCantConsDimen_D) {
        this.listaCantConsDimen_D = listaCantConsDimen_D;
    }

    public List<PoblacionCons> getListaCantConsDimen_E() {
        if (listaCantConsDimen_E == null) {
            listaCantConsDimen_E = new ArrayList<>();
        }           
        return listaCantConsDimen_E;
    }

    public void setListaCantConsDimen_E(List<PoblacionCons> listaCantConsDimen_E) {
        this.listaCantConsDimen_E = listaCantConsDimen_E;
    }

    public List<PoblacionCons> getListaCantConsDimen_F() {
        if (listaCantConsDimen_F == null) {
            listaCantConsDimen_F = new ArrayList<>();
        }           
        return listaCantConsDimen_F;
    }

    public void setListaCantConsDimen_F(List<PoblacionCons> listaCantConsDimen_F) {
        this.listaCantConsDimen_F = listaCantConsDimen_F;
    }

    public List<PoblacionCons> getListaCantConsDimen_G() {
        if (listaCantConsDimen_G == null) {
            listaCantConsDimen_G = new ArrayList<>();
        }           
        return listaCantConsDimen_G;
    }

    public void setListaCantConsDimen_G(List<PoblacionCons> listaCantConsDimen_G) {
        this.listaCantConsDimen_G = listaCantConsDimen_G;
    }

    public List<PoblacionCons> getListaCantConsDimen_H() {
        if (listaCantConsDimen_H == null) {
            listaCantConsDimen_H = new ArrayList<>();
        }           
        return listaCantConsDimen_H;
    }

    public void setListaCantConsDimen_H(List<PoblacionCons> listaCantConsDimen_H) {
        this.listaCantConsDimen_H = listaCantConsDimen_H;
    }

    public List<PoblacionCons> getListaCantConsDimen_I() {
        if (listaCantConsDimen_I == null) {
            listaCantConsDimen_I = new ArrayList<>();
        }           
        return listaCantConsDimen_I;
    }

    public void setListaCantConsDimen_I(List<PoblacionCons> listaCantConsDimen_I) {
        this.listaCantConsDimen_I = listaCantConsDimen_I;
    }

    public List<PoblacionCons> getListaCantConsDimen_J() {
        if (listaCantConsDimen_J == null) {
            listaCantConsDimen_J = new ArrayList<>();
        }           
        return listaCantConsDimen_J;
    }

    public void setListaCantConsDimen_J(List<PoblacionCons> listaCantConsDimen_J) {
        this.listaCantConsDimen_J = listaCantConsDimen_J;
    }

    public List<PoblacionCons> getListaCantConsDimen_K() {
        if (listaCantConsDimen_K == null) {
            listaCantConsDimen_K = new ArrayList<>();
        }           
        return listaCantConsDimen_K;
    }

    public void setListaCantConsDimen_K(List<PoblacionCons> listaCantConsDimen_K) {
        this.listaCantConsDimen_K = listaCantConsDimen_K;
    }

    public List<PoblacionCons> getListaCantConsDimen_L() {
        if (listaCantConsDimen_L == null) {
            listaCantConsDimen_L = new ArrayList<>();
        }           
        return listaCantConsDimen_L;
    }

    public void setListaCantConsDimen_L(List<PoblacionCons> listaCantConsDimen_L) {
        this.listaCantConsDimen_L = listaCantConsDimen_L;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_A() {
        if (listaCantConsGrpOcu_A == null) {
            listaCantConsGrpOcu_A = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_A;
    }

    public void setListaCantConsGrpOcu_A(List<PoblacionGrOcuBean> listaCantConsGrpOcu_A) {
        this.listaCantConsGrpOcu_A = listaCantConsGrpOcu_A;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_B() {
        if (listaCantConsGrpOcu_B == null) {
            listaCantConsGrpOcu_B = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_B;
    }

    public void setListaCantConsGrpOcu_B(List<PoblacionGrOcuBean> listaCantConsGrpOcu_B) {
        this.listaCantConsGrpOcu_B = listaCantConsGrpOcu_B;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_C() {
        if (listaCantConsGrpOcu_C == null) {
            listaCantConsGrpOcu_C = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_C;
    }

    public void setListaCantConsGrpOcu_C(List<PoblacionGrOcuBean> listaCantConsGrpOcu_C) {
        this.listaCantConsGrpOcu_C = listaCantConsGrpOcu_C;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_D() {
        if (listaCantConsGrpOcu_D == null) {
            listaCantConsGrpOcu_D = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_D;
    }

    public void setListaCantConsGrpOcu_D(List<PoblacionGrOcuBean> listaCantConsGrpOcu_D) {
        this.listaCantConsGrpOcu_D = listaCantConsGrpOcu_D;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_E() {
        if (listaCantConsGrpOcu_E == null) {
            listaCantConsGrpOcu_E = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_E;
    }

    public void setListaCantConsGrpOcu_E(List<PoblacionGrOcuBean> listaCantConsGrpOcu_E) {
        this.listaCantConsGrpOcu_E = listaCantConsGrpOcu_E;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_F() {
        if (listaCantConsGrpOcu_F == null) {
            listaCantConsGrpOcu_F = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_F;
    }

    public void setListaCantConsGrpOcu_F(List<PoblacionGrOcuBean> listaCantConsGrpOcu_F) {
        this.listaCantConsGrpOcu_F = listaCantConsGrpOcu_F;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_G() {
        if (listaCantConsGrpOcu_G == null) {
            listaCantConsGrpOcu_G = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_G;
    }

    public void setListaCantConsGrpOcu_G(List<PoblacionGrOcuBean> listaCantConsGrpOcu_G) {
        this.listaCantConsGrpOcu_G = listaCantConsGrpOcu_G;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_H() {
        if (listaCantConsGrpOcu_H == null) {
            listaCantConsGrpOcu_H = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_H;
    }

    public void setListaCantConsGrpOcu_H(List<PoblacionGrOcuBean> listaCantConsGrpOcu_H) {
        this.listaCantConsGrpOcu_H = listaCantConsGrpOcu_H;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_I() {
        if (listaCantConsGrpOcu_I == null) {
            listaCantConsGrpOcu_I = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_I;
    }

    public void setListaCantConsGrpOcu_I(List<PoblacionGrOcuBean> listaCantConsGrpOcu_I) {
        this.listaCantConsGrpOcu_I = listaCantConsGrpOcu_I;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_J() {
        if (listaCantConsGrpOcu_J == null) {
            listaCantConsGrpOcu_J = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_J;
    }

    public void setListaCantConsGrpOcu_J(List<PoblacionGrOcuBean> listaCantConsGrpOcu_J) {
        this.listaCantConsGrpOcu_J = listaCantConsGrpOcu_J;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_K() {
        if (listaCantConsGrpOcu_K == null) {
            listaCantConsGrpOcu_K = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_K;
    }

    public void setListaCantConsGrpOcu_K(List<PoblacionGrOcuBean> listaCantConsGrpOcu_K) {
        this.listaCantConsGrpOcu_K = listaCantConsGrpOcu_K;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_L() {
        if (listaCantConsGrpOcu_L == null) {
            listaCantConsGrpOcu_L = new ArrayList<>();
        }           
        return listaCantConsGrpOcu_L;
    }

    public void setListaCantConsGrpOcu_L(List<PoblacionGrOcuBean> listaCantConsGrpOcu_L) {
        this.listaCantConsGrpOcu_L = listaCantConsGrpOcu_L;
    }

    public Integer getCant_Consolidado() {
        return cant_Consolidado;
    }

    public void setCant_Consolidado(Integer cant_Consolidado) {
        this.cant_Consolidado = cant_Consolidado;
    }

    public BarChartModel getBarSatHisUMCH() {
        return barSatHisUMCH;
    }

    public void setBarSatHisUMCH(BarChartModel barSatHisUMCH) {
        this.barSatHisUMCH = barSatHisUMCH;
    }

    public List<HistoricoBean> getListaSatHisUMCH() {
        if (listaSatHisUMCH == null) {
            listaSatHisUMCH = new ArrayList<>();
        }            
        return listaSatHisUMCH;
    }

    public void setListaSatHisUMCH(List<HistoricoBean> listaSatHisUMCH) {
        this.listaSatHisUMCH = listaSatHisUMCH;
    }

    public BarChartModel getBarSatHisSTAMAR() {
        return barSatHisSTAMAR;
    }

    public void setBarSatHisSTAMAR(BarChartModel barSatHisSTAMAR) {
        this.barSatHisSTAMAR = barSatHisSTAMAR;
    }

    public List<HistoricoBean> getListaSatHisSTAMAR() {
        if (listaSatHisSTAMAR == null) {
            listaSatHisSTAMAR = new ArrayList<>();
        }            
        return listaSatHisSTAMAR;
    }

    public void setListaSatHisSTAMAR(List<HistoricoBean> listaSatHisSTAMAR) {
        this.listaSatHisSTAMAR = listaSatHisSTAMAR;
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

    public BarChartModel getBarSatHisCRISTC() {
        return barSatHisCRISTC;
    }

    public void setBarSatHisCRISTC(BarChartModel barSatHisCRISTC) {
        this.barSatHisCRISTC = barSatHisCRISTC;
    }

    public BarChartModel getBarSatHisSANJOS() {
        return barSatHisSANJOS;
    }

    public void setBarSatHisSANJOS(BarChartModel barSatHisSANJOS) {
        this.barSatHisSANJOS = barSatHisSANJOS;
    }

    public List<HistoricoBean> getListaSatHisSANJOS() {
        if (listaSatHisSANJOS == null) {
            listaSatHisSANJOS = new ArrayList<>();
        }           
        return listaSatHisSANJOS;
    }

    public void setListaSatHisSANJOS(List<HistoricoBean> listaSatHisSANJOS) {
        this.listaSatHisSANJOS = listaSatHisSANJOS;
    }

    public List<HistoricoBean> getListaSatHisCRISTC() {
        if (listaSatHisCRISTC == null) {
            listaSatHisCRISTC = new ArrayList<>();
        }           
        return listaSatHisCRISTC;
    }

    public void setListaSatHisCRISTC(List<HistoricoBean> listaSatHisCRISTC) {
        this.listaSatHisCRISTC = listaSatHisCRISTC;
    }

    public BarChartModel getBarSatisfaccionGral_est_part() {
        return barSatisfaccionGral_est_part;
    }

    public void setBarSatisfaccionGral_est_part(BarChartModel barSatisfaccionGral_est_part) {
        this.barSatisfaccionGral_est_part = barSatisfaccionGral_est_part;
    }

    public List<SatisfaccionGrlBean> getListaSatisfaccionGral_est_part() {
        if (listaSatisfaccionGral_est_part == null) {
            listaSatisfaccionGral_est_part = new ArrayList<>();
        }          
        return listaSatisfaccionGral_est_part;
    }

    public void setListaSatisfaccionGral_est_part(List<SatisfaccionGrlBean> listaSatisfaccionGral_est_part) {
        this.listaSatisfaccionGral_est_part = listaSatisfaccionGral_est_part;
    }

    public BarChartModel getBarResDimensionSecPeru_est_part() {
        return barResDimensionSecPeru_est_part;
    }

    public void setBarResDimensionSecPeru_est_part(BarChartModel barResDimensionSecPeru_est_part) {
        this.barResDimensionSecPeru_est_part = barResDimensionSecPeru_est_part;
    }

    public List<ResDimensionBean> getListaResDimensionSecPerubar_est_part() {
        if (listaResDimensionSecPerubar_est_part == null) {
            listaResDimensionSecPerubar_est_part = new ArrayList<>();
        }          
        return listaResDimensionSecPerubar_est_part;
    }

    public void setListaResDimensionSecPerubar_est_part(List<ResDimensionBean> listaResDimensionSecPerubar_est_part) {
        this.listaResDimensionSecPerubar_est_part = listaResDimensionSecPerubar_est_part;
    }

    public List<PoblacionCons> getListaCantConsUnineg_est_part() {
        if (listaCantConsUnineg_est_part == null) {
            listaCantConsUnineg_est_part = new ArrayList<>();
        }             
        return listaCantConsUnineg_est_part;
    }

    public void setListaCantConsUnineg_est_part(List<PoblacionCons> listaCantConsUnineg_est_part) {
        this.listaCantConsUnineg_est_part = listaCantConsUnineg_est_part;
    }

    public String getTip_colegio() {
        return tip_colegio;
    }

    public void setTip_colegio(String tip_colegio) {
        this.tip_colegio = tip_colegio;
    }

    public List<CantidadBean> getListaCantConGeneral_est_part() {
        if (listaCantConGeneral_est_part == null) {
            listaCantConGeneral_est_part = new ArrayList<>();
        }           
        return listaCantConGeneral_est_part;
    }

    public void setListaCantConGeneral_est_part(List<CantidadBean> listaCantConGeneral_est_part) {
        this.listaCantConGeneral_est_part = listaCantConGeneral_est_part;
    }

    public Integer getCant_Consolidado_est_part() {
        return cant_Consolidado_est_part;
    }

    public void setCant_Consolidado_est_part(Integer cant_Consolidado_est_part) {
        this.cant_Consolidado_est_part = cant_Consolidado_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegA_est_part() {
        if (listaGeneralUninegA_est_part == null) {
            listaGeneralUninegA_est_part = new ArrayList<>();
        }            
        return listaGeneralUninegA_est_part;
    }

    public void setListaGeneralUninegA_est_part(List<ConGeneralBean> listaGeneralUninegA_est_part) {
        this.listaGeneralUninegA_est_part = listaGeneralUninegA_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegB_est_part() {
        if (listaGeneralUninegB_est_part == null) {
            listaGeneralUninegB_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegB_est_part;
    }

    public void setListaGeneralUninegB_est_part(List<ConGeneralBean> listaGeneralUninegB_est_part) {
        this.listaGeneralUninegB_est_part = listaGeneralUninegB_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegC_est_part() {
        if (listaGeneralUninegC_est_part == null) {
            listaGeneralUninegC_est_part = new ArrayList<>();
        }           
        return listaGeneralUninegC_est_part;
    }

    public void setListaGeneralUninegC_est_part(List<ConGeneralBean> listaGeneralUninegC_est_part) {
        this.listaGeneralUninegC_est_part = listaGeneralUninegC_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegD_est_part() {
        if (listaGeneralUninegD_est_part == null) {
            listaGeneralUninegD_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegD_est_part;
    }

    public void setListaGeneralUninegD_est_part(List<ConGeneralBean> listaGeneralUninegD_est_part) {
        this.listaGeneralUninegD_est_part = listaGeneralUninegD_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegE_est_part() {
        if (listaGeneralUninegE_est_part == null) {
            listaGeneralUninegE_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegE_est_part;
    }

    public void setListaGeneralUninegE_est_part(List<ConGeneralBean> listaGeneralUninegE_est_part) {
        this.listaGeneralUninegE_est_part = listaGeneralUninegE_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegF_est_part() {
        if (listaGeneralUninegF_est_part == null) {
            listaGeneralUninegF_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegF_est_part;
    }

    public void setListaGeneralUninegF_est_part(List<ConGeneralBean> listaGeneralUninegF_est_part) {
        this.listaGeneralUninegF_est_part = listaGeneralUninegF_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegG_est_part() {
        if (listaGeneralUninegG_est_part == null) {
            listaGeneralUninegG_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegG_est_part;
    }

    public void setListaGeneralUninegG_est_part(List<ConGeneralBean> listaGeneralUninegG_est_part) {
        this.listaGeneralUninegG_est_part = listaGeneralUninegG_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegH_est_part() {
        if (listaGeneralUninegH_est_part == null) {
            listaGeneralUninegH_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegH_est_part;
    }

    public void setListaGeneralUninegH_est_part(List<ConGeneralBean> listaGeneralUninegH_est_part) {
        this.listaGeneralUninegH_est_part = listaGeneralUninegH_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegI_est_part() {
        if (listaGeneralUninegI_est_part == null) {
            listaGeneralUninegI_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegI_est_part;
    }

    public void setListaGeneralUninegI_est_part(List<ConGeneralBean> listaGeneralUninegI_est_part) {
        this.listaGeneralUninegI_est_part = listaGeneralUninegI_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegJ_est_part() {
        if (listaGeneralUninegJ_est_part == null) {
            listaGeneralUninegJ_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegJ_est_part;
    }

    public void setListaGeneralUninegJ_est_part(List<ConGeneralBean> listaGeneralUninegJ_est_part) {
        this.listaGeneralUninegJ_est_part = listaGeneralUninegJ_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegK_est_part() {
        if (listaGeneralUninegK_est_part == null) {
            listaGeneralUninegK_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegK_est_part;
    }

    public void setListaGeneralUninegK_est_part(List<ConGeneralBean> listaGeneralUninegK_est_part) {
        this.listaGeneralUninegK_est_part = listaGeneralUninegK_est_part;
    }

    public List<ConGeneralBean> getListaGeneralUninegL_est_part() {
        if (listaGeneralUninegL_est_part == null) {
            listaGeneralUninegL_est_part = new ArrayList<>();
        }          
        return listaGeneralUninegL_est_part;
    }

    public void setListaGeneralUninegL_est_part(List<ConGeneralBean> listaGeneralUninegL_est_part) {
        this.listaGeneralUninegL_est_part = listaGeneralUninegL_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegA_est_part() {
        if (listaGrpGeneralUninegA_est_part == null) {
            listaGrpGeneralUninegA_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegA_est_part;
    }

    public void setListaGrpGeneralUninegA_est_part(List<ConGeneralBean> listaGrpGeneralUninegA_est_part) {
        this.listaGrpGeneralUninegA_est_part = listaGrpGeneralUninegA_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegB_est_part() {
        if (listaGrpGeneralUninegB_est_part == null) {
            listaGrpGeneralUninegB_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegB_est_part;
    }

    public void setListaGrpGeneralUninegB_est_part(List<ConGeneralBean> listaGrpGeneralUninegB_est_part) {
        this.listaGrpGeneralUninegB_est_part = listaGrpGeneralUninegB_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegC_est_part() {
        if (listaGrpGeneralUninegC_est_part == null) {
            listaGrpGeneralUninegC_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegC_est_part;
    }

    public void setListaGrpGeneralUninegC_est_part(List<ConGeneralBean> listaGrpGeneralUninegC_est_part) {
        this.listaGrpGeneralUninegC_est_part = listaGrpGeneralUninegC_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegD_est_part() {
        if (listaGrpGeneralUninegD_est_part == null) {
            listaGrpGeneralUninegD_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegD_est_part;
    }

    public void setListaGrpGeneralUninegD_est_part(List<ConGeneralBean> listaGrpGeneralUninegD_est_part) {
        this.listaGrpGeneralUninegD_est_part = listaGrpGeneralUninegD_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegE_est_part() {
        if (listaGrpGeneralUninegE_est_part == null) {
            listaGrpGeneralUninegE_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegE_est_part;
    }

    public void setListaGrpGeneralUninegE_est_part(List<ConGeneralBean> listaGrpGeneralUninegE_est_part) {
        this.listaGrpGeneralUninegE_est_part = listaGrpGeneralUninegE_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegF_est_part() {
        if (listaGrpGeneralUninegF_est_part == null) {
            listaGrpGeneralUninegF_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegF_est_part;
    }

    public void setListaGrpGeneralUninegF_est_part(List<ConGeneralBean> listaGrpGeneralUninegF_est_part) {
        this.listaGrpGeneralUninegF_est_part = listaGrpGeneralUninegF_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegG_est_part() {
        if (listaGrpGeneralUninegG_est_part == null) {
            listaGrpGeneralUninegG_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegG_est_part;
    }

    public void setListaGrpGeneralUninegG_est_part(List<ConGeneralBean> listaGrpGeneralUninegG_est_part) {
        this.listaGrpGeneralUninegG_est_part = listaGrpGeneralUninegG_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegH_est_part() {
        if (listaGrpGeneralUninegH_est_part == null) {
            listaGrpGeneralUninegH_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegH_est_part;
    }

    public void setListaGrpGeneralUninegH_est_part(List<ConGeneralBean> listaGrpGeneralUninegH_est_part) {
        this.listaGrpGeneralUninegH_est_part = listaGrpGeneralUninegH_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegI_est_part() {
        if (listaGrpGeneralUninegI_est_part == null) {
            listaGrpGeneralUninegI_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegI_est_part;
    }

    public void setListaGrpGeneralUninegI_est_part(List<ConGeneralBean> listaGrpGeneralUninegI_est_part) {
        this.listaGrpGeneralUninegI_est_part = listaGrpGeneralUninegI_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegJ_est_part() {
        if (listaGrpGeneralUninegJ_est_part == null) {
            listaGrpGeneralUninegJ_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegJ_est_part;
    }

    public void setListaGrpGeneralUninegJ_est_part(List<ConGeneralBean> listaGrpGeneralUninegJ_est_part) {
        this.listaGrpGeneralUninegJ_est_part = listaGrpGeneralUninegJ_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegK_est_part() {
        if (listaGrpGeneralUninegK_est_part == null) {
            listaGrpGeneralUninegK_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegK_est_part;
    }

    public void setListaGrpGeneralUninegK_est_part(List<ConGeneralBean> listaGrpGeneralUninegK_est_part) {
        this.listaGrpGeneralUninegK_est_part = listaGrpGeneralUninegK_est_part;
    }

    public List<ConGeneralBean> getListaGrpGeneralUninegL_est_part() {
        if (listaGrpGeneralUninegL_est_part == null) {
            listaGrpGeneralUninegL_est_part = new ArrayList<>();
        }             
        return listaGrpGeneralUninegL_est_part;
    }

    public void setListaGrpGeneralUninegL_est_part(List<ConGeneralBean> listaGrpGeneralUninegL_est_part) {
        this.listaGrpGeneralUninegL_est_part = listaGrpGeneralUninegL_est_part;
    }

    public BarChartModel getBarGeneralUninegA_est_part() {
        return barGeneralUninegA_est_part;
    }

    public void setBarGeneralUninegA_est_part(BarChartModel barGeneralUninegA_est_part) {
        this.barGeneralUninegA_est_part = barGeneralUninegA_est_part;
    }

    public BarChartModel getBarGeneralUninegB_est_part() {
        return barGeneralUninegB_est_part;
    }

    public void setBarGeneralUninegB_est_part(BarChartModel barGeneralUninegB_est_part) {
        this.barGeneralUninegB_est_part = barGeneralUninegB_est_part;
    }

    public BarChartModel getBarGeneralUninegC_est_part() {
        return barGeneralUninegC_est_part;
    }

    public void setBarGeneralUninegC_est_part(BarChartModel barGeneralUninegC_est_part) {
        this.barGeneralUninegC_est_part = barGeneralUninegC_est_part;
    }

    public BarChartModel getBarGeneralUninegD_est_part() {
        return barGeneralUninegD_est_part;
    }

    public void setBarGeneralUninegD_est_part(BarChartModel barGeneralUninegD_est_part) {
        this.barGeneralUninegD_est_part = barGeneralUninegD_est_part;
    }

    public BarChartModel getBarGeneralUninegE_est_part() {
        return barGeneralUninegE_est_part;
    }

    public void setBarGeneralUninegE_est_part(BarChartModel barGeneralUninegE_est_part) {
        this.barGeneralUninegE_est_part = barGeneralUninegE_est_part;
    }

    public BarChartModel getBarGeneralUninegF_est_part() {
        return barGeneralUninegF_est_part;
    }

    public void setBarGeneralUninegF_est_part(BarChartModel barGeneralUninegF_est_part) {
        this.barGeneralUninegF_est_part = barGeneralUninegF_est_part;
    }

    public BarChartModel getBarGeneralUninegG_est_part() {
        return barGeneralUninegG_est_part;
    }

    public void setBarGeneralUninegG_est_part(BarChartModel barGeneralUninegG_est_part) {
        this.barGeneralUninegG_est_part = barGeneralUninegG_est_part;
    }

    public BarChartModel getBarGeneralUninegH_est_part() {
        return barGeneralUninegH_est_part;
    }

    public void setBarGeneralUninegH_est_part(BarChartModel barGeneralUninegH_est_part) {
        this.barGeneralUninegH_est_part = barGeneralUninegH_est_part;
    }

    public BarChartModel getBarGeneralUninegI_est_part() {
        return barGeneralUninegI_est_part;
    }

    public void setBarGeneralUninegI_est_part(BarChartModel barGeneralUninegI_est_part) {
        this.barGeneralUninegI_est_part = barGeneralUninegI_est_part;
    }

    public BarChartModel getBarGeneralUninegJ_est_part() {
        return barGeneralUninegJ_est_part;
    }

    public void setBarGeneralUninegJ_est_part(BarChartModel barGeneralUninegJ_est_part) {
        this.barGeneralUninegJ_est_part = barGeneralUninegJ_est_part;
    }

    public BarChartModel getBarGeneralUninegK_est_part() {
        return barGeneralUninegK_est_part;
    }

    public void setBarGeneralUninegK_est_part(BarChartModel barGeneralUninegK_est_part) {
        this.barGeneralUninegK_est_part = barGeneralUninegK_est_part;
    }

    public BarChartModel getBarGeneralUninegL_est_part() {
        return barGeneralUninegL_est_part;
    }

    public void setBarGeneralUninegL_est_part(BarChartModel barGeneralUninegL_est_part) {
        this.barGeneralUninegL_est_part = barGeneralUninegL_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegA_est_part() {
        return barGrpGeneralUninegA_est_part;
    }

    public void setBarGrpGeneralUninegA_est_part(BarChartModel barGrpGeneralUninegA_est_part) {
        this.barGrpGeneralUninegA_est_part = barGrpGeneralUninegA_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegB_est_part() {
        return barGrpGeneralUninegB_est_part;
    }

    public void setBarGrpGeneralUninegB_est_part(BarChartModel barGrpGeneralUninegB_est_part) {
        this.barGrpGeneralUninegB_est_part = barGrpGeneralUninegB_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegC_est_part() {
        return barGrpGeneralUninegC_est_part;
    }

    public void setBarGrpGeneralUninegC_est_part(BarChartModel barGrpGeneralUninegC_est_part) {
        this.barGrpGeneralUninegC_est_part = barGrpGeneralUninegC_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegD_est_part() {
        return barGrpGeneralUninegD_est_part;
    }

    public void setBarGrpGeneralUninegD_est_part(BarChartModel barGrpGeneralUninegD_est_part) {
        this.barGrpGeneralUninegD_est_part = barGrpGeneralUninegD_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegE_est_part() {
        return barGrpGeneralUninegE_est_part;
    }

    public void setBarGrpGeneralUninegE_est_part(BarChartModel barGrpGeneralUninegE_est_part) {
        this.barGrpGeneralUninegE_est_part = barGrpGeneralUninegE_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegF_est_part() {
        return barGrpGeneralUninegF_est_part;
    }

    public void setBarGrpGeneralUninegF_est_part(BarChartModel barGrpGeneralUninegF_est_part) {
        this.barGrpGeneralUninegF_est_part = barGrpGeneralUninegF_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegG_est_part() {
        return barGrpGeneralUninegG_est_part;
    }

    public void setBarGrpGeneralUninegG_est_part(BarChartModel barGrpGeneralUninegG_est_part) {
        this.barGrpGeneralUninegG_est_part = barGrpGeneralUninegG_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegH_est_part() {
        return barGrpGeneralUninegH_est_part;
    }

    public void setBarGrpGeneralUninegH_est_part(BarChartModel barGrpGeneralUninegH_est_part) {
        this.barGrpGeneralUninegH_est_part = barGrpGeneralUninegH_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegI_est_part() {
        return barGrpGeneralUninegI_est_part;
    }

    public void setBarGrpGeneralUninegI_est_part(BarChartModel barGrpGeneralUninegI_est_part) {
        this.barGrpGeneralUninegI_est_part = barGrpGeneralUninegI_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegJ_est_part() {
        return barGrpGeneralUninegJ_est_part;
    }

    public void setBarGrpGeneralUninegJ_est_part(BarChartModel barGrpGeneralUninegJ_est_part) {
        this.barGrpGeneralUninegJ_est_part = barGrpGeneralUninegJ_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegK_est_part() {
        return barGrpGeneralUninegK_est_part;
    }

    public void setBarGrpGeneralUninegK_est_part(BarChartModel barGrpGeneralUninegK_est_part) {
        this.barGrpGeneralUninegK_est_part = barGrpGeneralUninegK_est_part;
    }

    public BarChartModel getBarGrpGeneralUninegL_est_part() {
        return barGrpGeneralUninegL_est_part;
    }

    public void setBarGrpGeneralUninegL_est_part(BarChartModel barGrpGeneralUninegL_est_part) {
        this.barGrpGeneralUninegL_est_part = barGrpGeneralUninegL_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_A_est_part() {
        if ( listaCantConsDimen_A_est_part== null) {
             listaCantConsDimen_A_est_part= new ArrayList<>();
        }             
        return listaCantConsDimen_A_est_part;
    }

    public void setListaCantConsDimen_A_est_part(List<PoblacionCons> listaCantConsDimen_A_est_part) {
        this.listaCantConsDimen_A_est_part = listaCantConsDimen_A_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_B_est_part() {
        if ( listaCantConsDimen_B_est_part== null) {
             listaCantConsDimen_B_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_B_est_part;
    }

    public void setListaCantConsDimen_B_est_part(List<PoblacionCons> listaCantConsDimen_B_est_part) {
        this.listaCantConsDimen_B_est_part = listaCantConsDimen_B_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_C_est_part() {
        if ( listaCantConsDimen_C_est_part== null) {
             listaCantConsDimen_C_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_C_est_part;
    }

    public void setListaCantConsDimen_C_est_part(List<PoblacionCons> listaCantConsDimen_C_est_part) {
        this.listaCantConsDimen_C_est_part = listaCantConsDimen_C_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_D_est_part() {
        if (listaCantConsDimen_D_est_part == null) {
             listaCantConsDimen_D_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_D_est_part;
    }

    public void setListaCantConsDimen_D_est_part(List<PoblacionCons> listaCantConsDimen_D_est_part) {
        this.listaCantConsDimen_D_est_part = listaCantConsDimen_D_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_E_est_part() {
        if ( listaCantConsDimen_E_est_part== null) {
             listaCantConsDimen_E_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_E_est_part;
    }

    public void setListaCantConsDimen_E_est_part(List<PoblacionCons> listaCantConsDimen_E_est_part) {
        this.listaCantConsDimen_E_est_part = listaCantConsDimen_E_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_F_est_part() {
        if ( listaCantConsDimen_F_est_part== null) {
             listaCantConsDimen_F_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_F_est_part;
    }

    public void setListaCantConsDimen_F_est_part(List<PoblacionCons> listaCantConsDimen_F_est_part) {
        this.listaCantConsDimen_F_est_part = listaCantConsDimen_F_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_G_est_part() {
        if ( listaCantConsDimen_G_est_part== null) {
             listaCantConsDimen_G_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_G_est_part;
    }

    public void setListaCantConsDimen_G_est_part(List<PoblacionCons> listaCantConsDimen_G_est_part) {
        this.listaCantConsDimen_G_est_part = listaCantConsDimen_G_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_H_est_part() {
        if ( listaCantConsDimen_H_est_part== null) {
             listaCantConsDimen_H_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_H_est_part;
    }

    public void setListaCantConsDimen_H_est_part(List<PoblacionCons> listaCantConsDimen_H_est_part) {
        this.listaCantConsDimen_H_est_part = listaCantConsDimen_H_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_I_est_part() {
        if ( listaCantConsDimen_I_est_part== null) {
             listaCantConsDimen_I_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_I_est_part;
    }

    public void setListaCantConsDimen_I_est_part(List<PoblacionCons> listaCantConsDimen_I_est_part) {
        this.listaCantConsDimen_I_est_part = listaCantConsDimen_I_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_J_est_part() {
        if ( listaCantConsDimen_J_est_part== null) {
             listaCantConsDimen_J_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_J_est_part;
    }

    public void setListaCantConsDimen_J_est_part(List<PoblacionCons> listaCantConsDimen_J_est_part) {
        this.listaCantConsDimen_J_est_part = listaCantConsDimen_J_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_K_est_part() {
        if (listaCantConsDimen_K_est_part == null) {
             listaCantConsDimen_K_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_K_est_part;
    }

    public void setListaCantConsDimen_K_est_part(List<PoblacionCons> listaCantConsDimen_K_est_part) {
        this.listaCantConsDimen_K_est_part = listaCantConsDimen_K_est_part;
    }

    public List<PoblacionCons> getListaCantConsDimen_L_est_part() {
        if ( listaCantConsDimen_L_est_part== null) {
             listaCantConsDimen_L_est_part= new ArrayList<>();
        }         
        return listaCantConsDimen_L_est_part;
    }

    public void setListaCantConsDimen_L_est_part(List<PoblacionCons> listaCantConsDimen_L_est_part) {
        this.listaCantConsDimen_L_est_part = listaCantConsDimen_L_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_A_est_part() {
        if ( listaCantConsGrpOcu_A_est_part== null) {
             listaCantConsGrpOcu_A_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_A_est_part;
    }

    public void setListaCantConsGrpOcu_A_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_A_est_part) {
        this.listaCantConsGrpOcu_A_est_part = listaCantConsGrpOcu_A_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_B_est_part() {
        if ( listaCantConsGrpOcu_B_est_part== null) {
             listaCantConsGrpOcu_B_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_B_est_part;
    }

    public void setListaCantConsGrpOcu_B_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_B_est_part) {
        this.listaCantConsGrpOcu_B_est_part = listaCantConsGrpOcu_B_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_C_est_part() {
        if ( listaCantConsGrpOcu_C_est_part== null) {
            listaCantConsGrpOcu_C_est_part = new ArrayList<>();
        }         
        return listaCantConsGrpOcu_C_est_part;
    }

    public void setListaCantConsGrpOcu_C_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_C_est_part) {
        this.listaCantConsGrpOcu_C_est_part = listaCantConsGrpOcu_C_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_D_est_part() {
        if ( listaCantConsGrpOcu_D_est_part== null) {
             listaCantConsGrpOcu_D_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_D_est_part;
    }

    public void setListaCantConsGrpOcu_D_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_D_est_part) {
        this.listaCantConsGrpOcu_D_est_part = listaCantConsGrpOcu_D_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_E_est_part() {
        if (listaCantConsGrpOcu_E_est_part == null) {
             listaCantConsGrpOcu_E_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_E_est_part;
    }

    public void setListaCantConsGrpOcu_E_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_E_est_part) {
        this.listaCantConsGrpOcu_E_est_part = listaCantConsGrpOcu_E_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_F_est_part() {
        if ( listaCantConsGrpOcu_F_est_part== null) {
             listaCantConsGrpOcu_F_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_F_est_part;
    }

    public void setListaCantConsGrpOcu_F_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_F_est_part) {
        this.listaCantConsGrpOcu_F_est_part = listaCantConsGrpOcu_F_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_G_est_part() {
        if ( listaCantConsGrpOcu_G_est_part== null) {
             listaCantConsGrpOcu_G_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_G_est_part;
    }

    public void setListaCantConsGrpOcu_G_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_G_est_part) {
        this.listaCantConsGrpOcu_G_est_part = listaCantConsGrpOcu_G_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_H_est_part() {
        if ( listaCantConsGrpOcu_H_est_part== null) {
            listaCantConsGrpOcu_H_est_part = new ArrayList<>();
        }         
        return listaCantConsGrpOcu_H_est_part;
    }

    public void setListaCantConsGrpOcu_H_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_H_est_part) {
        this.listaCantConsGrpOcu_H_est_part = listaCantConsGrpOcu_H_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_I_est_part() {
        if ( listaCantConsGrpOcu_I_est_part== null) {
             listaCantConsGrpOcu_I_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_I_est_part;
    }

    public void setListaCantConsGrpOcu_I_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_I_est_part) {
        this.listaCantConsGrpOcu_I_est_part = listaCantConsGrpOcu_I_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_J_est_part() {
        if ( listaCantConsGrpOcu_J_est_part== null) {
             listaCantConsGrpOcu_J_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_J_est_part;
    }

    public void setListaCantConsGrpOcu_J_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_J_est_part) {
        this.listaCantConsGrpOcu_J_est_part = listaCantConsGrpOcu_J_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_K_est_part() {
        if ( listaCantConsGrpOcu_K_est_part== null) {
             listaCantConsGrpOcu_K_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_K_est_part;
    }

    public void setListaCantConsGrpOcu_K_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_K_est_part) {
        this.listaCantConsGrpOcu_K_est_part = listaCantConsGrpOcu_K_est_part;
    }

    public List<PoblacionGrOcuBean> getListaCantConsGrpOcu_L_est_part() {
        if (listaCantConsGrpOcu_L_est_part == null) {
             listaCantConsGrpOcu_L_est_part= new ArrayList<>();
        }         
        return listaCantConsGrpOcu_L_est_part;
    }

    public void setListaCantConsGrpOcu_L_est_part(List<PoblacionGrOcuBean> listaCantConsGrpOcu_L_est_part) {
        this.listaCantConsGrpOcu_L_est_part = listaCantConsGrpOcu_L_est_part;
    }

    public BarChartModel getBarSatHisSECTOR() {
        return barSatHisSECTOR;
    }

    public void setBarSatHisSECTOR(BarChartModel barSatHisSECTOR) {
        this.barSatHisSECTOR = barSatHisSECTOR;
    }

    public List<HistoricoBean> getListaSatHisSECTOR() {
        if (listaSatHisSECTOR == null) {
             listaSatHisSECTOR= new ArrayList<>();
        }             
        return listaSatHisSECTOR;
    }

    public void setListaSatHisSECTOR(List<HistoricoBean> listaSatHisSECTOR) {
        this.listaSatHisSECTOR = listaSatHisSECTOR;
    }

    
    
    
    
    
    
    
    
    
    
}
