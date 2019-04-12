package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
//import pe.marista.sigma.bean.CategoriaConceptoBean;
//import pe.marista.sigma.bean.ConceptoCategoriaBean;

/**
 *
 * @author Administrador
 */
public interface ConceptoDAO {

    public List<ConceptoBean> obtenerPorTipo(TipoConceptoBean tipoConceptoBean) throws Exception;

    public ConceptoBean obtenerConceptoPorId(ConceptoBean conceptoBean) throws Exception;
    public ConceptoBean obtenerConceptoCuentasPorId(ConceptoBean conceptoBean) throws Exception;

    public List<ConceptoBean> obtenerConcepto() throws Exception;
    
    public List<ConceptoBean> obtenerConceptoNotIn(String uniNeg) throws Exception;

    public List<ConceptoBean> obtenerTodosEgresos() throws Exception;

    public void insertarConcepto(ConceptoBean conceptoBean) throws Exception;

    public void modificarConcepto(ConceptoBean conceptoBean) throws Exception;

    public void eliminarConcepto(ConceptoBean conceptoBean) throws Exception;

    public void cambiarEstado(ConceptoBean conceptoBean) throws Exception;

    public void eliminarPorTipo(TipoConceptoBean tipoConceptoBean) throws Exception; 
    
    //Datos de Presupuesto
    public List<ConceptoBean> obtenerPresupuestoTipo(@Param("mod") String mod) throws Exception;
    public Integer obtenerGradoAcaPorIdConcepto(Integer idConcepto) throws Exception;
    
    public Integer obtenerConceptoDesc(Integer idConcepto) throws Exception;
    
    public String validarSiTieneCr(Integer idConcepto) throws Exception;

}
