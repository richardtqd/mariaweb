/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.MovilidadBean;
import pe.marista.sigma.dao.MovilidadDAO;

/**
 *
 * @author MS002
 */
public class MovilidadService {
    
    private MovilidadDAO movilidadDAO;

    public List<MovilidadBean> ObtenerMovilidad() throws Exception {
        return movilidadDAO.ObtenerMovilidad();
    }

    @Transactional
    public void InsertarMovilidad(MovilidadBean movilidadBean) throws Exception {
        movilidadDAO.InsertarMovilidad(movilidadBean);
    }

    public MovilidadBean ObtenerMovilidadPorId(String idmovilidad) throws Exception {
        return movilidadDAO.ObtenerMovilidadPorId(idmovilidad);
    }
    
    @Transactional
    public void EliminarMovilidad(String idmovilidad) throws Exception {
        movilidadDAO.EliminarMovilidad(idmovilidad);
    }   

    @Transactional
    public void ActualizarMovilidad(MovilidadBean movilidadBean) throws Exception {
        movilidadDAO.ActualizarMovilidad(movilidadBean);
    }

    public List<MovilidadBean> obtenerListaMovilidad() throws Exception {
        return movilidadDAO.obtenerListaMovilidad();
    }

    public List<MovilidadBean> obtenerAutorizacion() throws Exception {
        return movilidadDAO.obtenerAutorizacion();
    }

    public List<MovilidadBean> obtenerFiltro(MovilidadBean movilidadBean) throws Exception {
        return movilidadDAO.obtenerFiltro(movilidadBean);//Posible
    }
            
    public MovilidadDAO getMovilidadDAO() {
        return movilidadDAO;
    }

    public void setMovilidadDAO(MovilidadDAO movilidadDAO) {
        this.movilidadDAO = movilidadDAO;
    }
            
}
