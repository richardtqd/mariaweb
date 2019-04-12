/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.EnfermedadBean;

/**
 *
 * @author MS001
 */
public interface EnfermedadDAO {
    
    public void insertarEnfermedad(EnfermedadBean enfermedadBean) throws Exception;

    public void modificarEnfermedad(EnfermedadBean enfermedadBean) throws Exception;

    public void eliminarEnfermedad(Integer idEnfermedad) throws Exception;

    public List<EnfermedadBean> obtenerEnfermedad() throws Exception;
   
    public EnfermedadBean obtenerEnfermedadPorId(Integer idEnfermedad) throws Exception;
    
    public List<EnfermedadBean> obtenerEnfermedadPorTipo(Integer idTipoEnfermedad) throws Exception;
}
