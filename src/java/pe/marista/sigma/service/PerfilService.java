/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilModuloBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;
import pe.marista.sigma.dao.ModuloDAO;
import pe.marista.sigma.dao.PerfilDAO;
import pe.marista.sigma.dao.UsuarioDAO;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author Administrador
 */
public class PerfilService {

    private PerfilDAO perfilDAO;
    private ModuloDAO moduloDAO;
    private UsuarioDAO usuarioDAO;

    public List<PerfilBean> obtenerTodos() throws Exception {
        return perfilDAO.obtenerTodos();
    }

    public List<PerfilBean> obtenerFiltro(PerfilBean perfilBean) throws Exception {
        return perfilDAO.obtenerFiltro(perfilBean);
    }

    public List<PerfilModuloBean> obtenerPorPerfil(PerfilBean perfilBean) throws Exception {
        return perfilDAO.obtenerPorPerfil(perfilBean);
    }
    public List<ModuloBean> obtenerModuloPorPerfil(Integer idPerfil) throws Exception {
        return perfilDAO.obtenerModuloPorPerfil(idPerfil);
    }
    public List<PerfilModuloBean> obtenerModulo() throws Exception {
        return perfilDAO.obtenerModulo();
    }
    public List<PerfilBean> obtenerUsarioPerfil(UsuarioBean usuarioBean) throws Exception {
        return perfilDAO.obtenerUsarioPerfil(usuarioBean);
    }

    public PerfilBean obtenerPorId(PerfilBean perfilBean) throws Exception {
        return perfilDAO.obtenerPorId(perfilBean);
    }
    public PerfilBean obtenerPerfilPorNombre(PerfilBean perfilBean) throws Exception {
        return perfilDAO.obtenerPerfilPorNombre(perfilBean);
    }
    public List<VistaBean> validaPerfil(VistaBean vistaBean) throws Exception {
        return perfilDAO.validaPerfil(vistaBean);
    }

    @Transactional
    public void insertarPerfil(PerfilBean perfilBean, List<ModuloBean> listaModuloOrig) throws Exception {
        UsuarioBean usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        perfilBean.setCreaPor(usuarioLogin.getUsuario());
        perfilDAO.insertar(perfilBean);
        List<ModuloBean> listaModuloBean = moduloDAO.obtenerTodos();
        for (Object objecto : listaModuloOrig) {
            PerfilModuloBean perfilModuloBean = new PerfilModuloBean();
            perfilModuloBean.setPerfilBean(perfilBean);
            perfilModuloBean.getModuloBean().setIdModulo(new Integer(objecto.toString()));
            perfilModuloBean.setCreaPor(usuarioLogin.getUsuario());
            perfilDAO.insertarPerfilModulo(perfilModuloBean);
//            for (int i = 0; i < listaModuloBean.size(); i++) {
//                if (listaModuloBean.get(i).getIdModulo().toString().equals(perfilModuloBean.getModuloBean().getIdModulo().toString())) {
//                    perfilModuloBean.getModuloBean().setIdModuloPadre(listaModuloBean.get(i).getIdModuloPadre());
//                    break;
//                }
//            }
//            insertarPerfilRecursive(listaModuloBean, perfilModuloBean.getModuloBean().getIdModuloPadre(), perfilModuloBean.getPerfilBean().getIdPerfil());
        }
    }

//    @Transactional
//    public void modificarPerfil(PerfilBean perfilBean, List<ModuloBean> listaModuloOrig) throws Exception {
//        perfilDAO.eliminarPerfilModulo(perfilBean);
//        perfilDAO.actualizar(perfilBean);
//        List<ModuloBean> listaModuloBean = moduloDAO.obtenerTodos();
//        for (Object objecto : listaModuloOrig) {
//            PerfilModuloBean perfilModuloBean = new PerfilModuloBean();
//            perfilModuloBean.setPerfilBean(perfilBean);
//            perfilModuloBean.getModuloBean().setIdModulo(new Integer(objecto.toString()));
//            perfilDAO.insertarPerfilModulo(perfilModuloBean);
//            for (int i = 0; i < listaModuloBean.size(); i++) {
//                if(listaModuloBean.get(i).getIdModulo().toString().equals(perfilModuloBean.getModuloBean().getIdModulo().toString())){
//                    perfilModuloBean.getModuloBean().setIdModuloPadre(listaModuloBean.get(i).getIdModulo());
//                    break;
//                }
//            }
//            insertarPerfilRecursive(listaModuloBean,perfilModuloBean.getModuloBean().getIdModuloPadre(), perfilModuloBean.getPerfilBean().getIdPerfil());
//        }
//    }
    @Transactional
    public void modificarPerfil(PerfilBean perfilBean, List<ModuloBean> listaModuloOrig) throws Exception {
        List<PerfilModuloBean> modulosActuales = perfilDAO.obtenerPorPerfil(perfilBean);
        List<PerfilModuloBean> moduloAquitar = new ArrayList<>();
        List<ModuloBean> listaModuloBean = moduloDAO.obtenerTodos();
        UsuarioBean usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        for (int i = 0; i < listaModuloOrig.size(); i++) {
            boolean existe = false;
            Object objeto = listaModuloOrig.get(i);
            for (int j = 0; j < modulosActuales.size(); j++) {
                if (objeto.toString().equals(modulosActuales.get(j).getIdModulo().toString())) {
                    existe = true;
                }
            }
            if (!existe) {
                PerfilModuloBean perfilModuloBean = new PerfilModuloBean();
                perfilModuloBean.setPerfilBean(perfilBean);
                perfilModuloBean.getModuloBean().setIdModulo(new Integer(objeto.toString()));
                perfilModuloBean.setCreaPor(usuarioLogin.getUsuario());
                for (int k = 0; k < listaModuloBean.size(); k++) {
                    if (listaModuloBean.get(k).getIdModulo().toString().equals(perfilModuloBean.getModuloBean().getIdModulo().toString())) {
                        perfilModuloBean.getModuloBean().setIdModuloPadre(listaModuloBean.get(k).getIdModuloPadre());
                        break;
                    }
                }
                perfilDAO.insertarPerfilModulo(perfilModuloBean);
                insertarPerfilRecursive(listaModuloBean, perfilModuloBean.getModuloBean().getIdModuloPadre(), perfilModuloBean.getPerfilBean().getIdPerfil());
            }
        }
//        for (int i = 0; i < moduloAquitar.size(); i++) {
//            if (moduloAquitar.get(i) != null) {
//                usuarioDAO.eliminarVistaModulo(moduloAquitar.get(i));
//                perfilDAO.eliminarPerfilModuloPorId(moduloAquitar.get(i));
//            }
//        }
//        List<ModuloBean> listaModuloBean = moduloDAO.obtenerTodos();
//        for (ModuloBean moduloBean : modulosAagregar) {
//            PerfilModuloBean perfilModuloBean = new PerfilModuloBean();
//            perfilModuloBean.setPerfilBean(perfilBean);
//            perfilModuloBean.getModuloBean().setIdModulo(moduloBean.getIdModulo());
//            insertarPerfilRecursive(listaModuloBean, moduloBean.getIdModuloPadre(), perfilModuloBean.getPerfilBean().getIdPerfil());
//        }
        perfilDAO.actualizar(perfilBean);
    }

    @Transactional
    public void eliminarPerfil(PerfilBean perfilBean) throws Exception {
        perfilDAO.eliminarPerfilModulo(perfilBean);
        perfilDAO.eliminar(perfilBean);
    }

    @Transactional
    public void cambiarEstado(PerfilBean perfilBean) throws Exception {
        perfilDAO.cambiarEstado(perfilBean);
    }

    //GEtter and Setter
    public PerfilDAO getPerfilDAO() {
        return perfilDAO;
    }

    public void setPerfilDAO(PerfilDAO perfilDAO) {
        this.perfilDAO = perfilDAO;
    }

    public ModuloDAO getModuloDAO() {
        return moduloDAO;
    }

    public void setModuloDAO(ModuloDAO moduloDAO) {
        this.moduloDAO = moduloDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void insertarPerfilRecursive(List<ModuloBean> listaModuloBean, Integer idModuloPadre, Integer idPerfil) throws Exception {
        for (int i = 0; i < listaModuloBean.size(); i++) {
            if (listaModuloBean.get(i).getIdModulo().toString().equals(idModuloPadre.toString())) {
                UsuarioBean usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        
                PerfilModuloBean perfilModuloBean = new PerfilModuloBean();
                perfilModuloBean.setCreaPor(usuarioLogin.getUsuario());
                perfilModuloBean.getPerfilBean().setIdPerfil(idPerfil);
                perfilModuloBean.getModuloBean().setIdModulo(idModuloPadre);
                try {
                    if (idModuloPadre != 1) {
                        perfilDAO.insertarPerfilModulo(perfilModuloBean);
                    }
                } catch (Exception e) {
                    System.out.println("ya resgistrado");
                }
                if (idModuloPadre != 1) {
                    insertarPerfilRecursive(listaModuloBean, listaModuloBean.get(i).getIdModuloPadre(), idPerfil);
                }
            }
        }
    }
}
