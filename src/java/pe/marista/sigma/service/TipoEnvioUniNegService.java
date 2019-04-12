/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.TipoEnvioUniNegBean;
import pe.marista.sigma.dao.TipoEnvioUniNegDAO;

/**
 *
 * @author MS-001
 */
public class TipoEnvioUniNegService {
    private TipoEnvioUniNegDAO tipoEnvioUniNegDAO;

    public List<TipoEnvioUniNegBean> obtenerTipoEnvioUniNeg(String uniNeg) throws Exception {
        return tipoEnvioUniNegDAO.obtenerTipoEnvioUniNeg(uniNeg);
    }

    public TipoEnvioUniNegBean obtenerTipoEnvioPorId(Integer idTipoEnvioUniNeg, String uniNeg) throws Exception {
        return tipoEnvioUniNegDAO.obtenerTipoEnvioPorId(idTipoEnvioUniNeg, uniNeg);
    }
   
    
    
    //////////////
    public TipoEnvioUniNegDAO getTipoEnvioUniNegDAO() {
        return tipoEnvioUniNegDAO;
    }

    public void setTipoEnvioUniNegDAO(TipoEnvioUniNegDAO tipoEnvioUniNegDAO) {
        this.tipoEnvioUniNegDAO = tipoEnvioUniNegDAO;
    }
    
    
    
}
