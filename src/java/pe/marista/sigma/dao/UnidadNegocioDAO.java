/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.UnidadNegocioBean;

/**
 *
 * @author Administrador
 */
public interface UnidadNegocioDAO {

    public List<UnidadNegocioBean> obtenerTodos() throws Exception;
    public List<UnidadNegocioBean> obtenerTodosDef() throws Exception;
    public UnidadNegocioBean obtenerPorId(UnidadNegocioBean unidadNegocioBean) throws  Exception;
    public UnidadNegocioBean obtenerPorFiltro(UnidadNegocioBean unidadNegocioBean) throws Exception;
}
