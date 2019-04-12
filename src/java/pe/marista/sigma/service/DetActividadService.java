/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.util.List;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.dao.DetActividadDAO;

/**
 *
 * @author MS002
 */
public class DetActividadService {

    private DetActividadDAO detActividadDAO;

    public List<DetActividadBean> obtenerDetActividad(String uniNeg) throws Exception {
        return detActividadDAO.obtenerDetActividad(uniNeg);
    }

    public List<DetActividadBean> obtenerDetaPorID(Integer idActividad) throws Exception {
        return detActividadDAO.obtenerDetaPorID(idActividad);
    }

    public List<DetActividadBean> obtenerCuentaPorId(Integer cuenta) throws Exception {
        return detActividadDAO.obtenerCuentaPorId(cuenta);
    }

    public List<DetActividadBean> obtenerActCR(Integer idActividad, Integer cr, String uniNeg) throws Exception {
        return detActividadDAO.obtenerActCR(idActividad, cr, uniNeg);
    }

    public List<DetActividadBean> obtenerMesImporte(Integer idActividad, String uniNeg) throws Exception {
        return detActividadDAO.obtenerMesImporte(idActividad, uniNeg);
    }

    public void insertarDetActividad(DetActividadBean detActividadBean) throws Exception {
        detActividadDAO.insertarDetActividad(detActividadBean);
    }

    public void modificarDetActividad(DetActividadBean detActividadBean) throws Exception {
        detActividadDAO.modificarDetActividad(detActividadBean);
    }

    public void modificarDetaAct(DetActividadBean detActividadBean) throws Exception {
        detActividadDAO.modificarDetaAct(detActividadBean);
    }

    public void eliminarDetActividad(Integer idDetActividad) throws Exception {
        detActividadDAO.eliminarDetActividad(idDetActividad);
    }

    public DetActividadBean obtenerPorId(Integer idDetActividad) throws Exception {
        return detActividadDAO.obtenerPorId(idDetActividad);
    }

    public List<DetActividadBean> obtenerListaPorId(Integer idDetActividad) throws Exception {
        return detActividadDAO.obtenerListaPorId(idDetActividad);
    }

    public List<DetActividadBean> obtenerPresupuesto(String uniNeg) throws Exception {
        return detActividadDAO.obtenerPresupuesto(uniNeg);
    }

    public List<DetActividadBean> obtenerPorUniOrg(String uniNeg, Integer idUniOrg) throws Exception {
        return detActividadDAO.obtenerPorUniOrg(uniNeg, idUniOrg);
    }

    //Plan Contable
    public List<PlanContableBean> obtenerPlanContableFiltro(PlanContableBean planContableBean) throws Exception {
        return detActividadDAO.obtenerPlanContableFiltro(planContableBean);
    }

    public List<PlanContableBean> obtenerPlanContable() throws Exception {
        return detActividadDAO.obtenerPlanContable();
    }

    public PlanContableBean obtenerPlanPorId(Integer cuenta) throws Exception {
        return detActividadDAO.obtenerPlanPorId(cuenta);
    }

    public List<DetActividadBean> obtenerCuentas(String uniNeg, String cuenta) throws Exception {
        return detActividadDAO.obtenerCuentas(uniNeg, cuenta);
    }

    public List<DetActividadBean> obtenerDetaPorActividad(DetActividadBean detActividadBean) throws Exception {
        return detActividadDAO.obtenerDetaPorActividad(detActividadBean);
    }

    public BigDecimal obtenerImporteDetActividad(DetActividadBean detActividadBean) throws Exception {
        return detActividadDAO.obtenerImporteDetActividad(detActividadBean);
    }

    public DetActividadDAO getDetActividadDAO() {
        return detActividadDAO;
    }

    public void setDetActividadDAO(DetActividadDAO detActividadDAO) {
        this.detActividadDAO = detActividadDAO;
    }

}
