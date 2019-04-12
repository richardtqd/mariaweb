package pe.marista.sigma.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilModuloBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;
import pe.marista.sigma.dao.PerfilDAO;
import pe.marista.sigma.dao.UsuarioDAO;
import pe.marista.sigma.util.MaristaCifra;

public class UsuarioService {

    // Variables
    private UsuarioDAO usuarioDAO;
    private PerfilDAO perfilDAO;
//    private ModuloDAO moduloDAO;

    //METODOS USUARIO
//    public List<UsuarioBean> obtenerPorFiltro(UsuarioBean filtro)
//            throws Exception {
//        return usuarioDAO.obtenerPorFiltro(filtro);
//    }
//    public List<UsuarioBean> obtenerPorFiltroOrdenado(UsuarioBean filtro) throws Exception
//    {
//        return usuarioDAO.obtenerPorFiltroOrdenado(filtro);
//    }
    public UsuarioBean buscarPorId(String usuario) throws Exception {
        UsuarioBean usuarioBean = usuarioDAO.buscarPorId(usuario);
        usuarioBean.setClave(new MaristaCifra().decrypt(usuarioBean.getClave()));
        return usuarioBean;
    }
//    public List<UsuarioBean> buscarPorUsuario(String nomUsuario) throws Exception {
//        return usuarioDAO.buscarPorUsuario(nomUsuario);
//    }
//    public List<UsuarioPerfilBean> buscarPorCodUsuario(Integer codUsuario) throws Exception {
//        return usuarioDAO.buscarPorCodUsuario(codUsuario);
//    }

    public List<UsuarioBean> obtenerTodos() throws Exception {
        return usuarioDAO.obtenerTodos();
    }

    public List<UsuarioBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception {
        return usuarioDAO.obtenerPorUnidadNegocio(uniNeg);
    }
    
    
    

    public List<UsuarioBean> obtenerPorPersonal(PersonalBean personalBean) throws Exception {
        return usuarioDAO.obtenerPorPersonal(personalBean);
    }

    public List<UsuarioBean> obtenerPorFiltro(UsuarioBean usuarioBean) throws Exception {
        return usuarioDAO.obtenerPorFiltro(usuarioBean);
    }
    
     @Transactional
    public void insertarUsuarioMasivo(UsuarioBean usuarioBean) throws Exception {
        usuarioBean.setClave(new MaristaCifra().encrypt(usuarioBean.getClave())); 
        usuarioDAO.insertar(usuarioBean);
    }

    @Transactional
    public void insertarUsuario(UsuarioBean usuarioBean, List<PerfilBean> listaPerfilOrig,List<Integer> listaAcceso) throws Exception {
//        List<String> lista = listaPerfilOrig;
        usuarioBean.setClave(new MaristaCifra().encrypt(usuarioBean.getClave())); 
        usuarioDAO.insertar(usuarioBean);
        List<PerfilModuloBean> listaPerfilModuloBean = perfilDAO.obtenerTodosPerfilModulo();
        for (Object objecto : listaPerfilOrig) {
            VistaBean vistaBean = new VistaBean();
            vistaBean.setUsuarioBean(usuarioBean);
            for (int i = 0; i < listaPerfilModuloBean.size(); i++) {
                if (listaPerfilModuloBean.get(i).getIdPerfil().toString().equals(objecto.toString())) {
                    vistaBean.setPerfilModuloBean(listaPerfilModuloBean.get(i));
                    usuarioDAO.insertarVista(vistaBean);
                }
            }
        }
        String usuario=usuarioBean.getUsuario();
        String creaPor=usuarioBean.getModiPor();
        String uniNeg=usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
        usuarioDAO.eliminarUsuarioTipoAcceso(usuario,uniNeg);
        for(Integer lista : listaAcceso){
            usuarioDAO.insertarUsuarioTipoAcceso(usuario, uniNeg, lista, creaPor);
        }
    }

    @Transactional
    public void modificarUsuario(UsuarioBean usuarioBean, List<PerfilBean> listaPerfilOrig,List<Integer> listaAcceso) throws Exception {
//        List<String> lista = listaPerfilOrig;
        usuarioBean.setClave(new MaristaCifra().encrypt(usuarioBean.getClave()));
        usuarioDAO.eliminarVista(usuarioBean);
        usuarioDAO.actualizar(usuarioBean);
        List<PerfilModuloBean> listaPerfilModuloBean = perfilDAO.obtenerTodosPerfilModulo();
        for (Object objecto : listaPerfilOrig) {
            VistaBean vistaBean = new VistaBean();
            vistaBean.setUsuarioBean(usuarioBean);
            for (int i = 0; i < listaPerfilModuloBean.size(); i++) {
                if (listaPerfilModuloBean.get(i).getIdPerfil().toString().equals(objecto.toString())) {
                    vistaBean.setPerfilModuloBean(listaPerfilModuloBean.get(i));
                    usuarioDAO.insertarVista(vistaBean);
                }
            }
        }
        String usuario=usuarioBean.getUsuario();
        String creaPor=usuarioBean.getModiPor();
        String uniNeg=usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
        usuarioDAO.eliminarUsuarioTipoAcceso(usuario,uniNeg);
        for(Integer lista : listaAcceso){
            usuarioDAO.insertarUsuarioTipoAcceso(usuario, uniNeg, lista, creaPor);
        }
    }
    @Transactional
    public void modificarUsuarioContra(UsuarioBean usuarioBean) throws Exception {
//        List<String> lista = listaPerfilOrig;
        usuarioBean.setClave(new MaristaCifra().encrypt(usuarioBean.getClave()));
        usuarioDAO.actualizarContrasena(usuarioBean);        
    }

    @Transactional
    public void eliminarUsuario(UsuarioBean usuarioBean) throws Exception {
        usuarioDAO.eliminarVista(usuarioBean);
        usuarioDAO.eliminar(usuarioBean);
    }

    @Transactional
    public void cambiarEstado(UsuarioBean usuarioBean) throws Exception {
        usuarioDAO.cambiarEstado(usuarioBean);
    }

//    @Transactional
//    public void modificarUsuario(UsuarioBean bean) throws Exception {
////        bean.setClaUsuario(new MaristaCifra().encrypt(bean.getClaUsuario()));
//        usuarioDAO.actualizar(bean);
//    }
//    @Transactional
//    public void modificarUsuarioUpd(UsuarioBean bean) throws Exception {
////        bean.setClaUsuario(new MaristaCifra().encrypt(
////                bean.getClaUsuario()));
//        usuarioDAO.actualizar(bean);
//    }
//    @Transactional
//    public void eliminarUsuario(UsuarioBean bean) throws Exception {
//        usuarioDAO.eliminarLogicamente(bean);
//    }
//    @Transactional
//    public void cambiarEstadoUsuario(UsuarioBean bean) throws Exception {
//        usuarioDAO.cambiarEstado(bean);
//    }
//    //METODOS PARA EL PERFIL
//    @Transactional
//    public void insertarUsuarioPerfil(UsuarioBean bean, List<UsuarioPerfilBean> listaUsuPer) throws Exception {
//        for (UsuarioPerfilBean usuarioPerfilBean : listaUsuPer) {
//            usuarioPerfilBean.setUsuarioBean(bean);
//            usuarioDAO.insertarUsuarioPerfil(usuarioPerfilBean);
//        }
//    }
//    @Transactional
//    public void eliminarUsuarioPerfil(Integer codUsuario) throws Exception {
//        usuarioDAO.eliminarUsuarioPerfil(codUsuario);
//    }
    //Persona
    public List<PersonalBean> obtenerPersonalFiltro(PersonalBean personalBean) throws Exception {
        return usuarioDAO.obtenerPersonalFiltro(personalBean);
    }

    //Perfil Modulo

    public List<PerfilBean> obtenerVistaPerfilPorUsuario(UsuarioBean usuarioBean) throws Exception {
        return usuarioDAO.obtenerVistaPerfilPorUsuario(usuarioBean);

    }

    //Menu

    public List<ModuloBean> obtenerMenuPrincipal(String usuario, String uniNeg) throws Exception {
        return usuarioDAO.obtenerMenuPrincipal(usuario, uniNeg);
    }

    public List<ModuloBean> obtenerModulos() throws Exception {
        return usuarioDAO.obtenerModulos();
    }

    public List<VistaBean> obtenerUsuarioPorPerfil(VistaBean vistaBean) throws Exception {
        return usuarioDAO.obtenerUsuarioPorPerfil(vistaBean);
    }

    // Métodos
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

//    public ModuloDAO getModuloDAO() {
//        return moduloDAO;
//    }
//
//    public void setModuloDAO(ModuloDAO moduloDAO) {
//        this.moduloDAO = moduloDAO;
//    }
    public PerfilDAO getPerfilDAO() {
        return perfilDAO;
    }

    public void setPerfilDAO(PerfilDAO perfilDAO) {
        this.perfilDAO = perfilDAO;
    }

    public void insertarUsuarioTipoAcceso(String usuario, String uniNeg, Integer idTipoAcceso, String creaPor) throws Exception {
        usuarioDAO.insertarUsuarioTipoAcceso(usuario, uniNeg, idTipoAcceso, creaPor);
    }

    public void eliminarUsuarioTipoAcceso(String usuario, String uniNeg) throws Exception {
        usuarioDAO.eliminarUsuarioTipoAcceso(usuario, uniNeg);
    }

    public List<Integer> obtenerTipoNivelAccesoPorUsuario(String usuario, String uniNeg) throws Exception {
        return usuarioDAO.obtenerTipoNivelAccesoPorUsuario(usuario, uniNeg);
    }

    public UsuarioBean obtenerUsuarioIgual(String idPersonal) throws Exception {
        return usuarioDAO.obtenerUsuarioIgual(idPersonal);
    }

    public List<UsuarioBean> obtenerUsuariosActivos(String uniNeg) throws Exception {
        return usuarioDAO.obtenerUsuariosActivos(uniNeg);
    }

}
