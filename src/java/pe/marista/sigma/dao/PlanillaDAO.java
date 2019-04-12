package pe.marista.sigma.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PlanillaBean;
import pe.marista.sigma.bean.PlanillaCtsBean;
import pe.marista.sigma.bean.PlanillaCtsNoProcesadosBean;
import pe.marista.sigma.bean.PlanillaNoProcesadoBean;
import pe.marista.sigma.bean.TemporalBean;
import pe.marista.sigma.bean.reporte.PlanillaCtsReapBean;

public interface PlanillaDAO {

    public void insertarTemporal(TemporalBean temporalBean) throws Exception;

    public void insertarPlanillaCTS(PlanillaCtsBean planillaCtsBean) throws Exception;
    
    public void insertarPlanillaCTSNoProcesados(PlanillaCtsBean planillaCtsBean) throws Exception;

    public List<PlanillaBean> obtenerPlanilla(PlanillaBean planillaBean) throws Exception;

    public List<PlanillaBean> obtenerPlanillaMesAnio(PlanillaBean planillaBean) throws Exception;

    public Integer obtenerPlanillaUltimoId() throws Exception;

    public Integer obtenerPlanillaCantidadesInsertadas(Integer idPlanilla) throws Exception;
     
    public List<PlanillaBean> consultarTemporalConjunto() throws Exception;
    
    public List<PlanillaBean> consultarPlanillaConjunto(@Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public List<PlanillaBean> filtrarPlanilla(PlanillaBean planillaBean) throws Exception;
    
    public List<PlanillaCtsBean> filtrarPlanillacts(PlanillaCtsBean planillaCtsBean) throws Exception;

    public List<PlanillaBean> obtenerPlanillaListaProcesados(PlanillaBean planillaBean) throws Exception;

    public List<PlanillaNoProcesadoBean> obtenerPlanillaListaNoProcesados(@Param("listaPlanilla") List<String> listaPlanilla, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public List<PlanillaNoProcesadoBean> obtenerPlanillaListaNoProcesados2(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;
 
    public Object proPlanilla(PlanillaBean planillaBean) throws Exception;

    public Object proPlanillaAsiento(PlanillaBean planillaBean) throws Exception;

    public Double obtenerCantidadDeProcesados(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;
 
    public String obtenerUltimoDiaDelMes(@Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public String obtenerListaTrabajadoresPorMes(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("parte") Integer parte, @Param("codigo") String codigo) throws Exception;
    
    public String obtenerListaTrabajadoresPorMesNoProc(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("parte") Integer parte, @Param("codigo") String codigo) throws Exception;
    
    public Integer obtenerListaTrabajadoresCRparCts(@Param("uniNeg") String uniNeg, @Param("codigo") String codigo) throws Exception;
    
    public Object sp_ed_obtenerListaTrabajadoresCRparCts(PlanillaCtsBean planillaCtsBean) throws Exception;
    
    public List<PlanillaCtsReapBean> obtenerReporteCabecera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;
    
    public List<PlanillaCtsReapBean> obtenerReporteDetalle(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("idTipo") Integer idTipo) throws Exception;
    
    public List<PlanillaCtsReapBean> obtenerReporteSubDetalle(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("idTipo") Integer idTipo, @Param("idObjeto") Integer idObjeto) throws Exception;

    //cts
    public List<PlanillaCtsBean> obtenerPlanillaCTSListaNoProcesados2(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;
    
    public Double obtenerCantidadDeProcesadosCts(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;
    
    public List<PlanillaCtsNoProcesadosBean> obtenerPlanillaCTSListaNoProcesados(@Param("listaPlanilla") List<String> listaPlanilla, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;
}
