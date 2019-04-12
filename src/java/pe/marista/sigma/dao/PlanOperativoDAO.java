

package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PlanOperativoBean;


public interface PlanOperativoDAO
{
    public List<PlanOperativoBean> obtenerTodos() throws Exception;
    
    public List<PlanOperativoBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception;
    
    public List<PlanOperativoBean> obtenerPorUnidadNegocioUniOrg(@Param("idUniOrg") Integer idUniOrg, @Param("uniNeg") String uniNeg) throws Exception;
    
    public void insertarPlanOperativo(PlanOperativoBean planOperativoBean) throws Exception;
    
    public void modificarPlanOperativo(PlanOperativoBean planOperativoBean) throws Exception;
    

    public PlanOperativoBean eliminarPlanOperativoBean(Integer idUniOrg) throws Exception;

    public void eliminarPlanOperativoBean(PlanOperativoBean planOperativoBean) throws Exception;

    

    public void obtenerPlanOperativoPorId(Integer idUniOrg) throws Exception;

    public PlanOperativoBean obtenerPlanOperativoPorId(PlanOperativoBean planOperativoBean) throws Exception;

    public String obtenerCodigoPlanOperativo(String uniNeg) throws Exception;
    
    public List<PlanOperativoBean> obtenerPorFiltro(PlanOperativoBean planOperativoBean) throws Exception;
    
    
}
