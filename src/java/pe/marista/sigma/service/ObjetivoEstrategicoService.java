/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ObjetivoEstrategicaBean;
import pe.marista.sigma.dao.ObjetivoEstrategicoDAO;

/**
 *
 * @author MS002
 */
public class ObjetivoEstrategicoService {
    
    private ObjetivoEstrategicoDAO objetivoEstrategicoDAO;

    public List<ObjetivoEstrategicaBean> obtenerObjetivoEstrategico(String uniNeg) throws Exception {
        return objetivoEstrategicoDAO.obtenerObjetivoEstrategico(uniNeg);
    }

    public List<ObjetivoEstrategicaBean> obtenerObjetivoPorPlan(Integer idPlanEstrategico, String uniNeg) throws Exception {
        return objetivoEstrategicoDAO.obtenerObjetivoPorPlan(idPlanEstrategico, uniNeg);
    }
 
    public List<ObjetivoEstrategicaBean> obtenerObjPorLinea(Integer idLinea) throws Exception {
        return objetivoEstrategicoDAO.obtenerObjPorLinea(idLinea);
    }
        
    @Transactional
    public void insertarObjetivoEstrategico(ObjetivoEstrategicaBean objetivoEstrategicaBean) throws Exception {
        objetivoEstrategicoDAO.insertarObjetivoEstrategico(objetivoEstrategicaBean);
    }

    @Transactional
    public void modificarObjetivoEstrategico(ObjetivoEstrategicaBean objetivoEstrategicaBean) throws Exception {
        objetivoEstrategicoDAO.modificarObjetivoEstrategico(objetivoEstrategicaBean);
    }

    @Transactional
    public void eliminarObjetivoEstrategico(Integer idObjEstrategico) throws Exception {
        objetivoEstrategicoDAO.eliminarObjetivoEstrategico(idObjEstrategico);
    }

    public ObjetivoEstrategicaBean obtenerPorId(Integer idObjEstrategico) throws Exception {
        return objetivoEstrategicoDAO.obtenerPorId(idObjEstrategico);
    }

    public String obtenerCodObjetivoEstrategivo(String uniNeg) throws Exception {
        return objetivoEstrategicoDAO.obtenerCodObjetivoEstrategivo(uniNeg);
    }
    
    public ObjetivoEstrategicoDAO getObjetivoEstrategicoDAO() {
        return objetivoEstrategicoDAO;
    }

    public void setObjetivoEstrategicoDAO(ObjetivoEstrategicoDAO objetivoEstrategicoDAO) {
        this.objetivoEstrategicoDAO = objetivoEstrategicoDAO;
    }
 
}
