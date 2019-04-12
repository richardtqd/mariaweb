package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilModuloBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;

public interface UsuarioDAO {

    public List<UsuarioBean> obtenerTodos() throws Exception;

    public List<UsuarioBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception;

    public List<UsuarioBean> obtenerUsuariosActivos(String uniNeg) throws Exception;

    public UsuarioBean obtenerUsuarioIgual(String idPersonal) throws Exception;

    public List<UsuarioBean> obtenerPorFiltro(UsuarioBean usuarioBean) throws Exception;

    public List<UsuarioBean> obtenerPorPersonal(PersonalBean personalBean) throws Exception;

    public UsuarioBean buscarPorId(String usuario) throws Exception;

    public void insertar(UsuarioBean usuarioBean) throws Exception;

    public void actualizar(UsuarioBean usuarioBean) throws Exception;

    public void actualizarContrasena(UsuarioBean usuarioBean) throws Exception;

    public void eliminar(UsuarioBean usuarioBean) throws Exception;

    public void cambiarEstado(UsuarioBean usuarioBean) throws Exception;
//    public void actualizar(UsuarioBean usuarioBean) throws Exception;
//    public void eliminarLogicamente(UsuarioBean usuarioBean) throws Exception;
//    public void cambiarEstado(UsuarioBean usuarioBean) throws Exception;
//    public List<UsuarioBean> buscarPorUsuario(String nomUsuario) throws Exception;

    public UsuarioBean autenticarUsuario(UsuarioBean usuarioBean) throws Exception;
    //public List<String> obtenerOpcionesMenu(Integer codUsuario) throws Exception;

    //public List<UsuarioPerfilBean> buscarPorCodUsuario(Integer codUsuario) throws Exception;
    //public void insertarUsuarioPerfil(UsuarioPerfilBean usuarioPerfilBean) throws Exception;
    //public void actualizarUsuarioPerfil(UsuarioPerfilBean usuarioPerfilBean) throws Exception;
    //public void eliminarUsuarioPerfil(Integer codUsuario) throws Exception;
    //Modulos
    //Persona
    public List<PersonalBean> obtenerPersonalFiltro(PersonalBean personalBean) throws Exception;

    //Vista
    public void insertarVista(VistaBean vistaBean) throws Exception;

    public void eliminarVista(UsuarioBean usuarioBean) throws Exception;

    public void eliminarVistaModulo(PerfilModuloBean perfilModuloBean) throws Exception;

    public List<PerfilBean> obtenerVistaPerfilPorUsuario(UsuarioBean usuarioBean) throws Exception;

    public List<VistaBean> obtenerVistaPorUsuario(UsuarioBean usuarioBean) throws Exception;

    //mENU
    public List<ModuloBean> obtenerMenuPrincipal(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ModuloBean> obtenerModulos() throws Exception;

    public List<VistaBean> obtenerUsuarioPorPerfil(VistaBean vistaBean) throws Exception;

    //TipoAcceso
    public void insertarUsuarioTipoAcceso(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idTipoAcceso") Integer idTipoAcceso, @Param("creaPor") String creaPor) throws Exception;

    public void eliminarUsuarioTipoAcceso(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg) throws Exception;

    public List<Integer> obtenerTipoNivelAccesoPorUsuario(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg) throws Exception;

}
