/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.PaisBean;

/**
 *
 * @author Administrador
 */
public interface PaisDAO {
    public List<PaisBean> obtenerPais() throws Exception;
    
     public void insertarPais(PaisBean paisBean) throws Exception;

    public void modificarPais(PaisBean paisBean) throws Exception;

    public void eliminarPais(Integer idPais) throws Exception;
   
    public PaisBean obtenerPaisPorId(Integer idPais) throws Exception;

    public PaisBean obtenerPaisPorDefectoPeru(String nombre) throws Exception;
    
    
    
//    public List<PaisBean> obtenerPaisPorTipo(Integer idTipoEnfermedad) throws Exception;
    
}
