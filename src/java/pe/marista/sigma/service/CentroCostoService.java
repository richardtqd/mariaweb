/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.CentroCostoBean;
import pe.marista.sigma.dao.CentroCostoDAO;

/**
 *
 * @author Administrador
 */
public class CentroCostoService {
    
     private CentroCostoDAO centroCostoDAO;

    public List<CentroCostoBean> obtenerCentroCosto() throws Exception {
        return centroCostoDAO.obtenerCentroCosto();
    }

    public CentroCostoDAO getCentroCostoDAO() {
        return centroCostoDAO;
    }

    public void setCentroCostoDAO(CentroCostoDAO centroCostoDAO) {
        this.centroCostoDAO = centroCostoDAO;
    }

    
}
