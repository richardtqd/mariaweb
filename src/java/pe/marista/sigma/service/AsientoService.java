/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.dao.AsientoDAO;

/**
 *
 * @author Administrador
 */
public class AsientoService {
    
    private AsientoDAO asientoDAO;
    
    public List<CentroResponsabilidadBean> obtenerCRLiquidacion(AsientoBean asientoBean) throws Exception {
        return asientoDAO.obtenerCRLiquidacion(asientoBean);
    }
    
    public List<AsientoBean> obtenerCRLiq(Integer idObjeto, String uniNeg) throws Exception {
        return asientoDAO.obtenerCRLiq(idObjeto, uniNeg);
    }
    
    @Transactional
    public void insertarAsiento(AsientoBean asientoBean) throws Exception {
        asientoBean.setStatus(Boolean.TRUE);
        asientoDAO.insertarAsiento(asientoBean);
    }
    
    public AsientoDAO getAsientoDAO() {
        return asientoDAO;
    }
    
    public void setAsientoDAO(AsientoDAO asientoDAO) {
        this.asientoDAO = asientoDAO;
    }

    public void modificarAsientoAnulacion(String uniNeg, Integer idObjeto, String modiPor,String objeto) throws Exception {
        asientoDAO.modificarAsientoAnulacion(uniNeg, idObjeto, modiPor,objeto);
    }
    
}
