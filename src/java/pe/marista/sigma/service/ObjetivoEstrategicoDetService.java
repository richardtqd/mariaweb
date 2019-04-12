/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ObjetivoEstrategicoDetBean;
import pe.marista.sigma.dao.ObjetivoEstrategicoDetDAO;

/**
 *
 * @author MS002
 */
public class ObjetivoEstrategicoDetService {

    private ObjetivoEstrategicoDetDAO objetivoEstrategicoDetDAO;

    public List<ObjetivoEstrategicoDetBean> obtenerTodos() throws Exception {
        return objetivoEstrategicoDetDAO.obtenerTodos();
    }

    public List<ObjetivoEstrategicoDetBean> obtenerDetalles(Integer idObjEstrategico) throws Exception {
        return objetivoEstrategicoDetDAO.obtenerDetalles(idObjEstrategico);
    }

    public List<ObjetivoEstrategicoDetBean> obtenerEstDetFiltro(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) throws Exception {
        return objetivoEstrategicoDetDAO.obtenerEstDetFiltro(objetivoEstrategicoDetBean);
    }

    @Transactional
    public void insertarObjDet(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) throws Exception {
        objetivoEstrategicoDetDAO.insertarObjDet(objetivoEstrategicoDetBean);
    }

    @Transactional
    public void modificarObjDet(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) throws Exception {
        objetivoEstrategicoDetDAO.modificarObjDet(objetivoEstrategicoDetBean);
    }

    @Transactional
    public void eliminarObjDet(Integer idObjetivoEstrategicoDet) throws Exception {
        objetivoEstrategicoDetDAO.eliminarObjDet(idObjetivoEstrategicoDet);
    }

    public ObjetivoEstrategicoDetBean obtenerPorId(Integer idObjEstrategicoDet) throws Exception {
        return objetivoEstrategicoDetDAO.obtenerPorId(idObjEstrategicoDet);
    }

    public ObjetivoEstrategicoDetBean obetenerUltimoDet(String uniNeg) throws Exception {
        return objetivoEstrategicoDetDAO.obetenerUltimoDet(uniNeg);
    }

    public List<ObjetivoEstrategicoDetBean> obtenerListaPorId(Integer idObjEstrategicoDet) throws Exception {
        return objetivoEstrategicoDetDAO.obtenerListaPorId(idObjEstrategicoDet);
    }

    public List<ObjetivoEstrategicoDetBean> obtenerDetPorPlan(Integer idPlanEstrategico, String uniNeg) throws Exception {
        return objetivoEstrategicoDetDAO.obtenerDetPorPlan(idPlanEstrategico, uniNeg);
    }

    public List<ObjetivoEstrategicoDetBean> obtenerDetPorObj(String uniNeg, Integer idObjEstrategico) {
        return objetivoEstrategicoDetDAO.obtenerDetPorObj(uniNeg, idObjEstrategico);
    }

    public ObjetivoEstrategicoDetDAO getObjetivoEstrategicoDetDAO() {
        return objetivoEstrategicoDetDAO;
    }

    public void setObjetivoEstrategicoDetDAO(ObjetivoEstrategicoDetDAO objetivoEstrategicoDetDAO) {
        this.objetivoEstrategicoDetDAO = objetivoEstrategicoDetDAO;
    }

}
