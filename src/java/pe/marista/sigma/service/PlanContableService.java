package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.dao.PlanContableDAO;

/**
 *
 * @author Administrador
 */
public class PlanContableService {

    private PlanContableDAO planContableDAO;

    public List<PlanContableBean> obtenerPlanContable() throws Exception {
        return planContableDAO.obtenerPlanContable();
    }
    public List<PlanContableBean> obtenerPlanContableNombre() throws Exception {
        return planContableDAO.obtenerPlanContableNombre();
    }

    public List<PlanContableBean> obtenerPlanContableFiltro(PlanContableBean planContableBean) throws Exception {
        return planContableDAO.obtenerPlanContableFiltro(planContableBean);
    }

    public List<PlanContableBean> obtenerPlanFiltro() throws Exception {
        return planContableDAO.obtenerPlanFiltro();
    }

    public List<PlanContableBean> obtenerPlanFiltroCuenta(Integer cuenta) throws Exception {
        return planContableDAO.obtenerPlanFiltroCuenta(cuenta);
    }

    public List<PlanContableBean> obtenerPresupuestoUniNeg(String uniNeg) throws Exception {
        return planContableDAO.obtenerPresupuestoUniNeg(uniNeg);
    }

    public List<PlanContableBean> obtenerPresupuestoUniNegAnio(String uniNeg, Integer anio) throws Exception {
        return planContableDAO.obtenerPresupuestoUniNegAnio(uniNeg, anio);
    }

    public PlanContableDAO getPlanContableDAO() {
        return planContableDAO;
    }

    public void setPlanContableDAO(PlanContableDAO planContableDAO) {
        this.planContableDAO = planContableDAO;
    }

    public List<PlanContableBean> obtenerInCuentaAcceso(Integer idTipoAcceso, Integer anio, String uniNeg) throws Exception {
        return planContableDAO.obtenerInCuentaAcceso(idTipoAcceso, anio, uniNeg);
    }

    public List<PlanContableBean> obtenerOutCuentaAcceso(Integer idTipoAcceso, Integer anio, String uniNeg) throws Exception {
        return planContableDAO.obtenerOutCuentaAcceso(idTipoAcceso, anio, uniNeg);
    }

    public List<PlanContableBean> obtenerCuentaPorPresupuesto(Integer anio, String uniNeg) throws Exception {
        return planContableDAO.obtenerCuentaPorPresupuesto(anio, uniNeg);
    }

    public List<PlanContableBean> obtenerInCuentaAccesoPorNivel(List<Integer> ids, Integer anio, String uniNeg) throws Exception {
        return planContableDAO.obtenerInCuentaAccesoPorNivel(ids, anio, uniNeg);
    }

    public List<PlanContableBean> obtenerPlanContableIngresos() throws Exception {
        return planContableDAO.obtenerPlanContableIngresos();
    }

}
