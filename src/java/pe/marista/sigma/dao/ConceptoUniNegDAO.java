package pe.marista.sigma.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.TipoConceptoBean;

/**
 *
 * @author Administrador
 */
public interface ConceptoUniNegDAO {

    public ConceptoUniNegBean obtenerConceptoPorId(ConceptoUniNegBean conceptoUniNegBean) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoUniNeg() throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorTip(@Param("parms") Map<Object, Object> parms) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorUni(String uniNeg) throws Exception;
    
    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorUniArendir(String uniNeg) throws Exception;

//    public Integer obtenerTipoPorIdConcepto(Integer idConcepto) throws Exception;

    public Integer obtenerTipoPorProgramacion(@Param("idConcepto") Integer idConcepto, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerTipoPorIdConcepto(@Param("idConcepto") Integer idConcepto, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoUniNegActivos() throws Exception;
    
    public List<ConceptoUniNegBean> obtenerConceptoUniNegActivosIngresos() throws Exception;

    public void insertarConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception;

    public void modificarConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception;

    public void modificarMontoConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception;

    public void eliminarConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception;

    public void cambiarEstadoConceptoUniNeg(ConceptoUniNegBean conceptoUniNegBean) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptosEstudiante(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptosExAlumno(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptosExterno(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptosHijoExAlumno(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptosHijoEmpleado(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptosParametro(@Param("param") Map<String, String> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoEstMatri(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoInscr(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoPost(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoExterno(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoConProgramacion(@Param("param") Map<String, Object> param) throws Exception;
    
    public List<ConceptoUniNegBean> obtenerConceptoConProgramacionPorFiltro(@Param("param") Map<String, Object> param) throws Exception;

    public ConceptoUniNegBean obtenerConceptoPorIdConDscto(@Param("param") Map<String, Object> param) throws Exception;
    
    public ConceptoUniNegBean obtenerConceptoPorIdConCuotaIng(@Param("param") Map<String, Object> param) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorIng(String uniNeg) throws Exception;
    
    public List<ConceptoUniNegBean> obtenerConceptoUniNegCuotaIng(String uniNeg) throws Exception;

    public List<ConceptoUniNegBean> obtenerConceptoUniNegPorEgr(String uniNeg) throws Exception;

    public Integer obtenerPorIdConceptoMontoCero(@Param("idConcepto") Integer idConcepto, @Param("uniNeg") String uniNeg) throws Exception;

}
