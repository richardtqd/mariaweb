/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DiccionarioBean;
import pe.marista.sigma.dao.DiccionarioDAO;

/**
 *
 * @author MS002
 */
public class DiccionarioService{
    
    private DiccionarioDAO diccionarioDAO;
    
    public List<DiccionarioBean> obtenerDiccionario() throws Exception{
        return diccionarioDAO.obtenerDiccionario();
    }
    public List<DiccionarioBean> obtenerTablaDiccionario() throws Exception{
        return diccionarioDAO.obtenerTablaDiccionario();
    }
    public List<DiccionarioBean> obtenerColumnaDiccionario() throws Exception{
        return diccionarioDAO.obtenerColumnaDiccionario();
    }
    public List<DiccionarioBean> obtenerTipoDiccionario() throws Exception{
        return diccionarioDAO.obtenerTipoDiccionario();
    }
    public List<DiccionarioBean> obtenerTodosActivos() throws Exception{
        return diccionarioDAO.obtenerTodosActivos();
    }
     
    @Transactional
    public void modificarDiccionarioDescripcion(DiccionarioBean diccionarioBean) throws Exception{
        diccionarioDAO.modificarDiccionarioDescripcion(diccionarioBean);
    }
    
    public void ejecutarStoredProcedure(DiccionarioBean diccionarioBean) throws Exception{
        diccionarioDAO.ejecutarStoredProcedure(diccionarioBean);
    }
    
    public DiccionarioBean obtenerPorId(DiccionarioBean diccionarioBean) throws Exception{
       return diccionarioDAO.obtenerDiccionarioPorId(diccionarioBean);
    }
    
    public DiccionarioDAO getDiccionarioDAO() {
        return diccionarioDAO;
    }
    public void setDiccionarioDAO(DiccionarioDAO diccionarioDAO) {
        this.diccionarioDAO = diccionarioDAO;
    }
    
}
