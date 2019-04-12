package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PlanillaBean;
import pe.marista.sigma.bean.PlanillaCtsBean;
import pe.marista.sigma.bean.PlanillaCtsNoProcesadosBean;
import pe.marista.sigma.bean.PlanillaNoProcesadoBean;
import pe.marista.sigma.bean.TemporalBean;
import pe.marista.sigma.bean.reporte.PlanillaCtsReapBean;
import pe.marista.sigma.dao.PlanillaDAO;

public class PlanillaService {

    private PlanillaDAO planillaDAO;

    @Transactional
    public void insertarTemporal(TemporalBean temporalBean) throws Exception {
        planillaDAO.insertarTemporal(temporalBean);
    }

    @Transactional
    public void insertarPlanillaCTS(PlanillaCtsBean planillaCtsBean) throws Exception {
        planillaDAO.insertarPlanillaCTS(planillaCtsBean);
    }

    @Transactional
    public Object proPlanilla(PlanillaBean planillaBean) throws Exception {
        return planillaDAO.proPlanilla(planillaBean);
    }

    @Transactional
    public Object proPlanillaAsiento(PlanillaBean planillaBean) throws Exception {
        return planillaDAO.proPlanillaAsiento(planillaBean);
    }

    public List<PlanillaBean> obtenerPlanilla(PlanillaBean planillaBean) throws Exception {
        return planillaDAO.obtenerPlanilla(planillaBean);
    }

    public List<PlanillaBean> filtrarPlanilla(PlanillaBean planillaBean) throws Exception {
        return planillaDAO.filtrarPlanilla(planillaBean);
    }

    public PlanillaDAO getPlanillaDAO() {
        return planillaDAO;
    }

    public void setPlanillaDAO(PlanillaDAO planillaDAO) {
        this.planillaDAO = planillaDAO;
    }

    public List<PlanillaBean> obtenerPlanillaMesAnio(PlanillaBean planillaBean) throws Exception {
        return planillaDAO.obtenerPlanillaMesAnio(planillaBean);
    }

    public Integer obtenerPlanillaUltimoId() throws Exception {
        return planillaDAO.obtenerPlanillaUltimoId();
    }

    public Integer obtenerPlanillaCantidadesInsertadas(Integer idPlanilla) throws Exception {
        return planillaDAO.obtenerPlanillaCantidadesInsertadas(idPlanilla);
    }

    public List<PlanillaBean> obtenerPlanillaListaProcesados(PlanillaBean planillaBean) throws Exception {
        return planillaDAO.obtenerPlanillaListaProcesados(planillaBean);
    }

    public List<PlanillaNoProcesadoBean> obtenerPlanillaListaNoProcesados(List<String> listaPlanilla, String uniNeg, Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerPlanillaListaNoProcesados(listaPlanilla, uniNeg, anio, mes);
    }

    public List<PlanillaNoProcesadoBean> obtenerPlanillaListaNoProcesados2(String uniNeg, Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerPlanillaListaNoProcesados2(uniNeg, anio, mes);
    }

    public Double obtenerCantidadDeProcesados(String uniNeg, Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerCantidadDeProcesados(uniNeg, anio, mes);
    }

    public String obtenerUltimoDiaDelMes(Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerUltimoDiaDelMes(anio, mes);
    }

    public String obtenerListaTrabajadoresPorMes(String uniNeg, Integer anio, Integer parte, String codigo) throws Exception {
        return planillaDAO.obtenerListaTrabajadoresPorMes(uniNeg, anio, parte, codigo);
    }

    public void insertarPlanillaCTSNoProcesados(PlanillaCtsBean planillaCtsBean) throws Exception {
        planillaDAO.insertarPlanillaCTSNoProcesados(planillaCtsBean);
    }

    public Integer obtenerListaTrabajadoresCRparCts(String uniNeg, String codigo) throws Exception {
        return planillaDAO.obtenerListaTrabajadoresCRparCts(uniNeg, codigo);
    }

    public Object sp_ed_obtenerListaTrabajadoresCRparCts(PlanillaCtsBean planillaCtsBean) throws Exception {
        return planillaDAO.sp_ed_obtenerListaTrabajadoresCRparCts(planillaCtsBean);
    }

    public List<PlanillaCtsReapBean> obtenerReporteCabecera(String uniNeg, Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerReporteCabecera(uniNeg, anio, mes);
    }

    public List<PlanillaCtsReapBean> obtenerReporteDetalle(String uniNeg, Integer anio, Integer mes, Integer idTipo) throws Exception {
        return planillaDAO.obtenerReporteDetalle(uniNeg, anio, mes, idTipo);
    }

    public List<PlanillaCtsReapBean> obtenerReporteSubDetalle(String uniNeg, Integer anio, Integer mes, Integer idTipo, Integer idObjeto) throws Exception {
        return planillaDAO.obtenerReporteSubDetalle(uniNeg, anio, mes, idTipo, idObjeto);
    }

    public List<PlanillaCtsBean> filtrarPlanillacts(PlanillaCtsBean planillaCtsBean) throws Exception {
        return planillaDAO.filtrarPlanillacts(planillaCtsBean);
    }

    public List<PlanillaCtsBean> obtenerPlanillaCTSListaNoProcesados2(String uniNeg, Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerPlanillaCTSListaNoProcesados2(uniNeg, anio, mes);
    }

    public Double obtenerCantidadDeProcesadosCts(String uniNeg, Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerCantidadDeProcesadosCts(uniNeg, anio, mes);
    }

    public List<PlanillaCtsNoProcesadosBean> obtenerPlanillaCTSListaNoProcesados(List<String> listaPlanilla, String uniNeg, Integer anio, Integer mes) throws Exception {
        return planillaDAO.obtenerPlanillaCTSListaNoProcesados(listaPlanilla, uniNeg, anio, mes);
    }

    public String obtenerListaTrabajadoresPorMesNoProc(String uniNeg, Integer anio, Integer parte, String codigo) throws Exception {
        return planillaDAO.obtenerListaTrabajadoresPorMesNoProc(uniNeg, anio, parte, codigo);
    }
 
    public List<PlanillaBean> consultarTemporalConjunto() throws Exception {
        return planillaDAO.consultarTemporalConjunto();
    }

    public List<PlanillaBean> consultarPlanillaConjunto(Integer anio, Integer mes) throws Exception {
        return planillaDAO.consultarPlanillaConjunto(anio, mes);
    }
 
}
