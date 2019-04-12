/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EnfermedadBean;
import pe.marista.sigma.dao.EnfermedadDAO;

/**
 *
 * @author MS001
 */
public class EnfermedadService {
    
    private EnfermedadDAO enfermedadDAO;
    
    
//    Logica de Negocio
    @Transactional
    public void insertarEnfermedad(EnfermedadBean enfermedadBean) throws Exception {
        enfermedadDAO.insertarEnfermedad(enfermedadBean);
    }

    @Transactional
    public void modificarEnfermedad(EnfermedadBean enfermedadBean) throws Exception {
        enfermedadDAO.modificarEnfermedad(enfermedadBean);
    }

    @Transactional
    public void eliminarEnfermedad(Integer idEnfermedad) throws Exception {
        enfermedadDAO.eliminarEnfermedad(idEnfermedad);
    }

    public List<EnfermedadBean> obtenerEnfermedad() throws Exception {
        return enfermedadDAO.obtenerEnfermedad();
    }
    public List<EnfermedadBean> obtenerEnfermedadPorTipo(Integer idTipoEnfermedad) throws Exception {
        return enfermedadDAO.obtenerEnfermedadPorTipo(idTipoEnfermedad);
    }

    public EnfermedadBean obtenerEnfermedadPorId(Integer idEnfermedad) throws Exception {
        return enfermedadDAO.obtenerEnfermedadPorId(idEnfermedad);
    }
      
    //metodos gettes and setter
    public EnfermedadDAO getEnfermedadDAO() {
        return enfermedadDAO;
    }

    public void setEnfermedadDAO(EnfermedadDAO enfermedadDAO) {
        this.enfermedadDAO = enfermedadDAO;
    }
    
    
}
