/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.ProcesoFalloBean;
import pe.marista.sigma.dao.ProcesoFalloDAO;

/**
 *
 * @author JC
 */
public class ProcesoFalloService {

    private ProcesoFalloDAO procesoFalloDAO;

    public List<ProcesoFalloBean> obtenerFalloUniNeg(ProcesoFalloBean procesoFalloBean) throws Exception {
        return procesoFalloDAO.obtenerFalloUniNeg(procesoFalloBean);
    }

    public List<ProcesoFalloBean> obtenerFalloPorBanco(String uniNeg, Integer idProcesoBanco) throws Exception {
        return procesoFalloDAO.obtenerFalloPorBanco(uniNeg, idProcesoBanco);
    }

    public ProcesoFalloDAO getProcesoFalloDAO() {
        return procesoFalloDAO;
    }

    public void setProcesoFalloDAO(ProcesoFalloDAO procesoFalloDAO) {
        this.procesoFalloDAO = procesoFalloDAO;
    }
 
}
