/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilUnidadNegocioBean;
import pe.marista.sigma.bean.UnidadNegocioBean;

/**
 *
 * @author Administrador
 */
public interface PerfilUnidadNegocioDAO {


    public List<PerfilUnidadNegocioBean> obtenerTodos() throws Exception;

    public List<PerfilUnidadNegocioBean> obtenerPorFiltro(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception;

    public PerfilUnidadNegocioBean obtenerPorId(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception;
    public List<PerfilUnidadNegocioBean> obtenerPorIdPerfil(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception;

    public void insertar(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception;

    public void actualizar(PerfilBean perfilBean) throws Exception;

    public void eliminarLogicamente(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception;
    public void eliminarTodos(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception;
    
    //Perfil
    public void insertarPerfil(PerfilBean perfilBean) throws Exception;
    public void actualizarPerfil(PerfilBean perfilBean) throws Exception;
    public void eliminarPerfil(PerfilBean perfilBean) throws Exception;
    public List<PerfilBean> obtenerTodosPerfil() throws Exception;
    public List<PerfilBean> obtenerTodosFiltroPerfil(PerfilBean perfilBean) throws Exception;
    //Modulo 
    public List<ModuloBean> obtenerTodosModulo() throws Exception;
    //Unidad Negocio
    public List<UnidadNegocioBean> obtenerTodosUnidadNegocio() throws Exception;
}
