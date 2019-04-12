
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilUsuarioBean;


public interface PerfilModuloDAO 
{
    //listo todos los perfiles
    public List<PerfilUsuarioBean> obtenerTodos() throws Exception;
    //lista por filtro
    public List<PerfilUsuarioBean> obtenerPorFiltro(PerfilUsuarioBean perfilUsuarioBean) throws Exception;
    //obtengo el codigo del ultimo regsitro insertado
    public Integer obtenerUltCodigo () throws Exception;
    //obtengo un perfil
    public PerfilUsuarioBean buscarPorId(Integer codigo) throws Exception;
    //insertar un perfil
    public void insertarPerfil (PerfilUsuarioBean perfilUsuarioBean) throws Exception;
    //actualiza un perfil
    public void actualizarPerfil (PerfilUsuarioBean perfilUsuarioBean) throws Exception;
    //elimina un perfil
    public void eliminarPerfil (Integer codigo) throws Exception;
    
    
    //nueva vista
    //Perfil
    public void insertarPerfil(PerfilBean perfilBean) throws Exception;
    public void actualizarPerfil(PerfilBean perfilBean) throws Exception;
    public void eliminarPerfil(PerfilBean perfilBean) throws Exception;
    public List<PerfilBean> obtenerTodosPerfil() throws Exception;
    public List<PerfilBean> obtenerTodosFiltroPerfil(PerfilBean perfilBean) throws Exception;
}
