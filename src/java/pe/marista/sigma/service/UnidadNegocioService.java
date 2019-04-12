/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.dao.UnidadNegocioDAO;

/**
 *
 * @author Administrador
 */
public class UnidadNegocioService {

    //Variable

    private UnidadNegocioDAO unidadNegocioDAO;

    //Metodos Logica dew Negocio

    public List<UnidadNegocioBean> obtenerTodos() throws Exception {
        return unidadNegocioDAO.obtenerTodos();
    } 
    public List<UnidadNegocioBean> obtenerTodosDef() throws Exception {
        return unidadNegocioDAO.obtenerTodosDef();
    } 

    public UnidadNegocioBean obtenerPorFiltro(UnidadNegocioBean unidadNegocioBean) throws Exception {
        return unidadNegocioDAO.obtenerPorFiltro(unidadNegocioBean);
    }

    //Getter and Setter
    public UnidadNegocioDAO getUnidadNegocioDAO() {
        return unidadNegocioDAO;
    }

    public void setUnidadNegocioDAO(UnidadNegocioDAO unidadNegocioDAO) {
        this.unidadNegocioDAO = unidadNegocioDAO;
    }

    public UnidadNegocioBean obtenerPorId(UnidadNegocioBean unidadNegocioBean) throws Exception {
        return unidadNegocioDAO.obtenerPorId(unidadNegocioBean);
    }

}
