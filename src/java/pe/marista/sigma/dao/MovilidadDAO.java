/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.MovilidadBean;

/**
 *
 * @author MS002
 */
public interface MovilidadDAO {
    
    public List<MovilidadBean> ObtenerMovilidad() throws Exception;
   
    public void InsertarMovilidad(MovilidadBean movilidadBean) throws Exception;
    
    public MovilidadBean ObtenerMovilidadPorId(String idmovilidad) throws Exception;
   
    public void EliminarMovilidad(String idmovilidad) throws Exception;
   
    public void ActualizarMovilidad(MovilidadBean movilidadBean) throws Exception;
     
    public List<MovilidadBean> obtenerListaMovilidad() throws Exception;
    
    public List<MovilidadBean> obtenerAutorizacion() throws Exception;
    
    public List<MovilidadBean> obtenerFiltro(MovilidadBean movilidadBean) throws Exception;
}
