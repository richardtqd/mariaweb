/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.ProcesoErrorBean;
import pe.marista.sigma.dao.ProcesoErrorDAO;

/**
 *
 * @author MS002
 */
public class ProcesoErrorService {

    private ProcesoErrorDAO procesoErrorDAO;

    public List<ProcesoErrorBean> filtrarError(String uniNeg, String creaFecha) throws Exception {
        return procesoErrorDAO.filtrarError(uniNeg, creaFecha);
    }

    public List<ProcesoErrorBean> obtenerError(String uniNeg, Integer flgError) throws Exception {
        return procesoErrorDAO.obtenerError(uniNeg, flgError);
    }

    public List<ProcesoErrorBean> obtenerPorProcesoBanco(Integer idProcesoBanco, String uniNeg) throws Exception {
        return procesoErrorDAO.obtenerPorProcesoBanco(idProcesoBanco, uniNeg);
    }

    public ProcesoErrorDAO getProcesoErrorDAO() {
        return procesoErrorDAO;
    }

    public void setProcesoErrorDAO(ProcesoErrorDAO procesoErrorDAO) {
        this.procesoErrorDAO = procesoErrorDAO;
    }

}
