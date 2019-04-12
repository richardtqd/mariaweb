/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EstudianteMovilidadBean;
import pe.marista.sigma.dao.EstudianteMovilidadDAO;

/**
 *
 * @author MS002
 */
public class EstudianteMovilidadService {
    
    private EstudianteMovilidadDAO estudianteMovilidadDAO;

    public List<EstudianteMovilidadBean> obtenerEstudianteMovilidad(String idEstudiante) throws Exception {
        return estudianteMovilidadDAO.obtenerEstudianteMovilidad(idEstudiante);
    }

    public void insertarEstudianteMovilidad(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception {
        estudianteMovilidadDAO.insertarEstudianteMovilidad(estudianteMovilidadBean);
    }

    public void eliminarEstudianteMovilidad(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception {
        estudianteMovilidadDAO.eliminarEstudianteMovilidad(estudianteMovilidadBean);
    }

    public void actualizarEstudianteMovilidad(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception {
        estudianteMovilidadDAO.actualizarEstudianteMovilidad(estudianteMovilidadBean);
    }

    public EstudianteMovilidadBean obtenerPorId(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception {
        return estudianteMovilidadDAO.obtenerPorId(estudianteMovilidadBean);
    }

    public List<EstudianteMovilidadBean> obtenerEstudiantePorId(String idEstudiante) throws Exception {
        return estudianteMovilidadDAO.obtenerEstudiantePorId(idEstudiante);
    }

    public List<EstudianteMovilidadBean> obtenerMovilidadPorId(String idmovilidad) throws Exception {
        return estudianteMovilidadDAO.obtenerMovilidadPorId(idmovilidad);
    }

    public List<EstudianteMovilidadBean> obtenerEstudiante() throws Exception {
        return estudianteMovilidadDAO.obtenerEstudiante();
    }

    public List<EstudianteMovilidadBean> obtenerMovilidad() throws Exception {
        return estudianteMovilidadDAO.obtenerMovilidad();
    }

    public List<EstudianteMovilidadBean> obtenerMFiltro(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception {
        return estudianteMovilidadDAO.obtenerMFiltro(estudianteMovilidadBean);
    }

    public EstudianteMovilidadDAO getEstudianteMovilidadDAO() {
        return estudianteMovilidadDAO;
    }

    public void setEstudianteMovilidadDAO(EstudianteMovilidadDAO estudianteMovilidadDAO) {
        this.estudianteMovilidadDAO = estudianteMovilidadDAO;
    }
    
}
