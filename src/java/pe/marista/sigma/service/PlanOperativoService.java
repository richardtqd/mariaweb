
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.PlanOperativoBean;
import pe.marista.sigma.dao.PlanOperativoDAO;


public class PlanOperativoService
{
    
    private PlanOperativoDAO planOperativoDAO;

    public void insertarPlanOperativo(PlanOperativoBean planOperativoBean) throws Exception {
        planOperativoDAO.insertarPlanOperativo(planOperativoBean);
    }

    public void modificarPlanOperativo(PlanOperativoBean planOperativoBean) throws Exception {
        planOperativoDAO.modificarPlanOperativo(planOperativoBean);
    }

    public void eliminarPlanOperativoBean(PlanOperativoBean planOperativoBean) throws Exception {
        planOperativoDAO.eliminarPlanOperativoBean(planOperativoBean);
    }

    public PlanOperativoBean eliminarPlanOperativoBean(Integer idUniOrg) throws Exception {
        return planOperativoDAO.eliminarPlanOperativoBean(idUniOrg);
    }


    public PlanOperativoBean obtenerPlanOperativoPorId(PlanOperativoBean planOperativoBean) throws Exception {
        return planOperativoDAO.obtenerPlanOperativoPorId(planOperativoBean);
    }

    public String obtenerCodigoPlanOperativo(String uniNeg) throws Exception {
        return planOperativoDAO.obtenerCodigoPlanOperativo(uniNeg);
    }

    public List<PlanOperativoBean> obtenerPorFiltro(PlanOperativoBean plaOperativoBean) throws Exception {
        return planOperativoDAO.obtenerPorFiltro(plaOperativoBean);
    }
    
    //Metodos Lógica de Negocio
    public List<PlanOperativoBean> obtenerTodos() throws Exception{    
        return planOperativoDAO.obtenerTodos();
    }
    
    public List<PlanOperativoBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception{    
        return planOperativoDAO.obtenerPorUnidadNegocio(uniNeg); 
    }

    public PlanOperativoDAO getPlanOperativoDAO() {
        return planOperativoDAO;
    }

    public void setPlanOperativoDAO(PlanOperativoDAO planOperativoDAO) {
        this.planOperativoDAO = planOperativoDAO;
    }    

    public List<PlanOperativoBean> obtenerPorUnidadNegocioUniOrg(Integer idUniOrg, String uniNeg) throws Exception {
        return planOperativoDAO.obtenerPorUnidadNegocioUniOrg(idUniOrg, uniNeg);
    }
    
    
    
}
