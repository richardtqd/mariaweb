/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteMovilidadBean;
import pe.marista.sigma.bean.MovilidadBean;

/**
 *
 * @author MS002
 */
public interface EstudianteMovilidadDAO {
    
    public List<EstudianteMovilidadBean> obtenerEstudianteMovilidad(String idEstudiante) throws Exception;
    
    public void insertarEstudianteMovilidad(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception;
 
    public void eliminarEstudianteMovilidad(EstudianteMovilidadBean estudianteMovilidadBean) throws  Exception;

    public void actualizarEstudianteMovilidad(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception;

    public EstudianteMovilidadBean obtenerPorId(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception;
    
    public List<EstudianteMovilidadBean> obtenerEstudiantePorId(String idEstudiante)throws Exception;
    
    public List<EstudianteMovilidadBean> obtenerMovilidadPorId(String idmovilidad)throws Exception;
    
    public List<EstudianteMovilidadBean> obtenerEstudiante() throws Exception;
    
    public List<EstudianteMovilidadBean> obtenerMovilidad() throws Exception;
    
    public List<EstudianteMovilidadBean> obtenerMFiltro(EstudianteMovilidadBean estudianteMovilidadBean) throws Exception;
    
}
