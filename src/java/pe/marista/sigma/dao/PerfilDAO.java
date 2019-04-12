/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilModuloBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;

/**
 *
 * @author Administrador
 */
public interface PerfilDAO {

    public List<PerfilBean> obtenerTodos() throws Exception;
    public List<PerfilBean> obtenerFiltro(PerfilBean perfilBean) throws Exception;
    public PerfilBean obtenerPorId(PerfilBean perfilBean) throws Exception;
    public PerfilBean obtenerPerfilPorNombre(PerfilBean perfilBean) throws Exception;
    public void insertar(PerfilBean perfilBean) throws Exception;
    public void actualizar(PerfilBean perfilBean) throws Exception;
    public void eliminar(PerfilBean perfilBean) throws Exception;
    public void cambiarEstado(PerfilBean perfilBean) throws Exception;
    public List<PerfilBean> obtenerUsarioPerfil(UsuarioBean usuarioBean) throws Exception;
    public List<VistaBean> validaPerfil(VistaBean vistaBean) throws Exception;
       
    //Perfil Modulo
    public List<PerfilModuloBean> obtenerTodosPerfilModulo() throws Exception;
    public void insertarPerfilModulo(PerfilModuloBean perfilModuloBean) throws Exception;
    public void eliminarPerfilModulo(PerfilBean perfilBean) throws Exception;
    public void eliminarPerfilModuloPorId(PerfilModuloBean perfilModuloBean) throws Exception;
    public List<PerfilModuloBean> obtenerPorPerfil(PerfilBean perfilBean) throws Exception;
    public List<ModuloBean> obtenerModuloPorPerfil(Integer idPerfil) throws Exception;
    public List<PerfilModuloBean> obtenerModulo() throws Exception;
    public List<PerfilModuloBean> obtenerPorPerfil2(PerfilBean perfilBean) throws Exception;
}
