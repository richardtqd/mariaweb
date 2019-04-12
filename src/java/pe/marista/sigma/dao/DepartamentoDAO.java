/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.DepartamentoBean;
import pe.marista.sigma.bean.DistritoBean;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.bean.ProvinciaBean;

/**
 *
 * @author Administrador
 */
public interface DepartamentoDAO {
    
    public List<PaisBean> obtenerPais() throws Exception;
    
    public List<DepartamentoBean> obtenerDepartamento() throws Exception;
    
    public List<ProvinciaBean> obtenerProvincia() throws Exception;
    
    public List<DistritoBean> obtenerDistrito() throws Exception;
    
}
