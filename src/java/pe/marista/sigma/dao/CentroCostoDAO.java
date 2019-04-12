/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CentroCostoBean;

/**
 *
 * @author Administrador
 */
public interface CentroCostoDAO {
    
    public List<CentroCostoBean> obtenerCentroCosto() throws Exception;
    
//    
//    public List<CentroCostoBean> obtenerTodos() throws Exception;  
//   
//    public List<CentroCostoBean> obtenerPorFiltro(CentroCostoBean centroCostoBean) throws Exception;
//
//    public CentroCostoBean buscarPorId(int id) throws Exception;
//
//    public void insertar(CentroCostoBean centroCostoBean) throws Exception;
//    
//    public void actualizar(CentroCostoBean centroCostoBean) throws Exception;
//    
//    public void eliminar(CentroCostoBean centroCostoBean) throws Exception;
    
    
}

