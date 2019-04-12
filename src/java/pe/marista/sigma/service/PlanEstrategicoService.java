/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.LineaEstrategicaBean;
import pe.marista.sigma.bean.PlanEstrategicoBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.dao.LineaEstrategicaDAO;
import pe.marista.sigma.dao.PlanEstrategicoDAO;

/**
 *
 * @author MS002
 */
public class PlanEstrategicoService {

    //Variables 
    private PlanEstrategicoDAO planEstrategicoDAO;
    private LineaEstrategicaDAO lineaEstrategicaDAO;

    public List<PlanEstrategicoBean> obtenerTodos() throws Exception {
        return planEstrategicoDAO.obtenerTodos();
    }

    public List<PlanEstrategicoBean> obtenerPorFiltro(PlanEstrategicoBean planEstrategicoBean) throws Exception {
        return planEstrategicoDAO.obtenerPorFiltro(planEstrategicoBean);
    }

    public List<PlanEstrategicoBean> obtenerTodosUniNeg(String uniNeg) throws Exception {
        return planEstrategicoDAO.obtenerTodosUniNeg(uniNeg);
    }
 
    public PlanEstrategicoBean buscarPorId(Integer idPlanEstrategico) throws Exception {
        return planEstrategicoDAO.buscarPorId(idPlanEstrategico);
    }

    public PlanEstrategicoBean obtenerPorId(PlanEstrategicoBean planEstrategicoBean) throws Exception {
        return planEstrategicoDAO.obtenerPorId(planEstrategicoBean);
    }

    public PlanEstrategicoBean obtenerPlanId(Integer idPlanEstrategico) throws Exception {
        return planEstrategicoDAO.obtenerPlanId(idPlanEstrategico);
    }

    @Transactional
    public void insertar(PlanEstrategicoBean planEstrategicoBean) throws Exception {
        planEstrategicoDAO.insertar(planEstrategicoBean);
    }

    @Transactional
    public void actualizar(PlanEstrategicoBean planEstrategicoBean) throws Exception {
        planEstrategicoDAO.actualizar(planEstrategicoBean);
    }

    @Transactional
    public void eliminar(Integer idPlanEstrategico) throws Exception {
        planEstrategicoDAO.eliminar(idPlanEstrategico);
    }

    public List<UnidadOrganicaBean> obtenerUnidadOrg() throws Exception {
        return planEstrategicoDAO.obtenerUnidadOrg();
    }

    public String obtenerUltimoCodigo(PlanEstrategicoBean planEstrategicoBean) throws Exception {
        return planEstrategicoDAO.obtenerUltimoCodigo(planEstrategicoBean);
    }

    //Línea Estratégica
    public List<LineaEstrategicaBean> obtenerLineaEstrategica() throws Exception {
        return lineaEstrategicaDAO.obtenerLineaEstrategica();
    }

    public LineaEstrategicaBean obtenerPorId(Integer idLinea) throws Exception {
        return lineaEstrategicaDAO.obtenerPorId(idLinea);
    }

    @Transactional
    public void insertarLineaEstrategica(LineaEstrategicaBean lineaEstrategicaBean) throws Exception {
        lineaEstrategicaDAO.insertarLineaEstrategica(lineaEstrategicaBean);
    }

    @Transactional
    public void actualizarLineaEstrategica(LineaEstrategicaBean lineaEstrategicaBean) throws Exception {
        lineaEstrategicaDAO.actualizarLineaEstrategica(lineaEstrategicaBean);
    }

    @Transactional
    public void eliminarLineaEstrategica(Integer idLinea) throws Exception {
        lineaEstrategicaDAO.eliminarLineaEstrategica(idLinea);
    }

    public String obtenerUltimoCodigo(String uniNeg) throws Exception {
        return lineaEstrategicaDAO.obtenerUltimoCodigo(uniNeg);
    }

    public void cambiarEstado(LineaEstrategicaBean lineaEstrategicaBean) throws Exception {
        lineaEstrategicaDAO.cambiarEstado(lineaEstrategicaBean);
    }

    //Métodos
    public PlanEstrategicoDAO getPlanEstrategicoDAO() {
        return planEstrategicoDAO;
    }

    public void setPlanEstrategicoDAO(PlanEstrategicoDAO planEstrategicoDAO) {
        this.planEstrategicoDAO = planEstrategicoDAO;
    }

    public LineaEstrategicaDAO getLineaEstrategicaDAO() {
        return lineaEstrategicaDAO;
    }

    public void setLineaEstrategicaDAO(LineaEstrategicaDAO lineaEstrategicaDAO) {
        this.lineaEstrategicaDAO = lineaEstrategicaDAO;
    }

}
