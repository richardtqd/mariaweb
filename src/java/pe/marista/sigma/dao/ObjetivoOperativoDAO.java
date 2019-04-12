

package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ObjOperativoBean;


public interface ObjetivoOperativoDAO
{
    public List<ObjOperativoBean> obtenerTodos() throws Exception;
    
    public List<ObjOperativoBean> obtenerPorPlanOpe(Integer idObjOperativo) throws Exception;
    
    public void insertarObjetivoOperativo(ObjOperativoBean objOperativoBean) throws Exception;
    
    public void modificarObjetivoOperativo(ObjOperativoBean objOperativoBean) throws Exception;
    
    public void eliminarObjetivoOperativo(Integer idObjOperativo) throws Exception;
    
    public ObjOperativoBean obtenerPorId(Integer idObjOperativo) throws Exception;
    
    public List<ObjOperativoBean> obtenerPorPlanOperativo(@Param("idUniOrg")Integer idUniOrg,@Param("uniNeg")String uniNeg) throws Exception;
    
    public List<ObjOperativoBean> obtenerPorFiltroActividad(ObjOperativoBean objOperativoBean) throws Exception;
    
    public Integer obtenerUltimoCodigo(String uniNeg) throws Exception;
    
    public Integer obtenerMaxIdObjOperativo(String uniNeg) throws Exception;
    
    public List<ObjOperativoBean> obtenerPorMaxId(@Param("idObjOperativo")Integer idObjOperativo,@Param("uniNeg")String uniNeg) throws Exception;
    
    public List<ObjOperativoBean> obtenerActividad(CodigoBean codigoBean) throws Exception;
    
    public List<ObjOperativoBean> obtenerUniMed(CodigoBean codigoBean) throws Exception;
    
    public ObjOperativoBean obtenerPorIdObj(Integer idObjOperativo) throws Exception;
    
    public List<ObjOperativoBean> obtenerPorUniNeg(ObjOperativoBean objOperativoBean) throws Exception;
    
    public ObjOperativoBean obtenerPorObjDet(Integer idObjEstrategicoDet) throws Exception;
    
}
