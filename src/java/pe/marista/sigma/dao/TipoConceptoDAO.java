package pe.marista.sigma.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.TipoConceptoBean;

/**
 *
 * @author Administrador
 */
public interface TipoConceptoDAO {

    public void insertarTipoConcepto(TipoConceptoBean tipoConceptoBean) throws Exception;

    public void modificarTipoConcepto(TipoConceptoBean tipoConceptoBean) throws Exception;

    public void eliminarTipoConcepto(Integer idTipoConcepto) throws Exception;

    public List<TipoConceptoBean> obtenerTipoConcepto() throws Exception;

    public TipoConceptoBean obtenerTipoConceptoPorId(Integer idTipoConcepto) throws Exception;
    
    public List<TipoConceptoBean> obtenerListaTipoConceptoPorId(Integer idTipoConcepto) throws Exception;
    
    public List<TipoConceptoBean> obtenerTipoConceptoIngreso() throws Exception;
    
    public List<TipoConceptoBean> obtenerTipoConceptoSalida() throws Exception;
 
    public List<TipoConceptoBean> obtenerPorTipoProcesoBanco(@Param("parms")Map<String, Object> parms) throws Exception;  
    
    public List<TipoConceptoBean> obtenerPorTipoProcesoBancoCur(@Param("parms")Map<String, Object> parms) throws Exception; 
    
    public List<TipoConceptoBean> obtenerPorTipoCronograma(@Param("idTipoConcepto1") Integer idTipoConcepto1,@Param("idTipoConcepto2") Integer idTipoConcepto2) throws Exception;
    
     public List<TipoConceptoBean> obtenerConceptosTree(String uniNeg) throws Exception;
    
}
