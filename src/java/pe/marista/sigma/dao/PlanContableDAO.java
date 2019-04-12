package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PlanContableBean;

/**
 *
 * @author Administrador
 */
public interface PlanContableDAO {

    public List<PlanContableBean> obtenerPlanContable() throws Exception;
    
    public List<PlanContableBean> obtenerPlanContableIngresos() throws Exception;

    public List<PlanContableBean> obtenerPlanContableNombre() throws Exception;

    public List<PlanContableBean> obtenerPlanContableFiltro(PlanContableBean planContableBean) throws Exception;

    public List<PlanContableBean> obtenerPlanFiltro() throws Exception;

    public List<PlanContableBean> obtenerPlanFiltroCuenta(@Param("cuenta") Integer cuenta) throws Exception;

    public List<PlanContableBean> obtenerPresupuestoUniNeg(String uniNeg) throws Exception;

    public List<PlanContableBean> obtenerPresupuestoUniNegAnio(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<PlanContableBean> obtenerInCuentaAcceso(@Param("idTipoAcceso") Integer idTipoAcceso, @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;

    public List<PlanContableBean> obtenerOutCuentaAcceso(@Param("idTipoAcceso") Integer idTipoAcceso, @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;
    
    public List<PlanContableBean> obtenerCuentaPorPresupuesto(@Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;
    
    public List<PlanContableBean> obtenerInCuentaAccesoPorNivel(@Param("list") List<Integer> ids, @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;
}
