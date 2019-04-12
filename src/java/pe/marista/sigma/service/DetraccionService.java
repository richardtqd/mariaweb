/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetraccionBean;
import pe.marista.sigma.dao.DetraccionDAO;

/**
 *
 * @author Administrador
 */
public class DetraccionService {
    
    private DetraccionDAO detraccionDAO;
    
    public List<DetraccionBean> obtenerTodos() throws Exception{
        return detraccionDAO.obtenerTodos();
    }

    public List<DetraccionBean> obtenerTodosActivos() throws Exception{
        return detraccionDAO.obtenerTodosActivos();
    }
    
    public DetraccionBean obtenerPorId(DetraccionBean detraccionBean) throws Exception{
        return detraccionDAO.obtenerPorId(detraccionBean);
    }
    public Double redondearDetraccionAfavor(Double monto) throws Exception{
        return detraccionDAO.redondearDetraccionAfavor(monto);
    }

    @Transactional
    public void insertarDetraccion(DetraccionBean detraccionBean) throws Exception{
        detraccionDAO.insertarDetraccion(detraccionBean);
    }

    @Transactional
    public void modificarDetraccion(DetraccionBean detraccionBean) throws Exception{
        detraccionDAO.modificarDetraccion(detraccionBean);
    }

    @Transactional
    public void eliminarDetraccion(DetraccionBean detraccionBean) throws Exception{
        detraccionDAO.eliminarDetraccion(detraccionBean);
    }

    @Transactional
    public void cambiarEstadoDetraccion(DetraccionBean detraccionBean) throws Exception{
        detraccionDAO.cambiarEstadoDetraccion(detraccionBean);
    }
    
    
    //
    public DetraccionDAO getDetraccionDAO() {
        return detraccionDAO;
    }

    public void setDetraccionDAO(DetraccionDAO detraccionDAO) {
        this.detraccionDAO = detraccionDAO;
    }
    
}
