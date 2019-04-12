/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.dao.ModuloDAO;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class ModuloService {

    private ModuloDAO moduloDAO;

    public ModuloDAO getModuloDAO() {
        return moduloDAO;
    }

    public void setModuloDAO(ModuloDAO moduloDAO) {
        this.moduloDAO = moduloDAO;
    }

    //Metodos Logica de Negocio
    public List<ModuloBean> obtenerTodos() throws Exception {
        return moduloDAO.obtenerTodos();
    }

    public List<ModuloBean> obtenerFiltro(Integer idTipoNodo) throws Exception {
        return moduloDAO.obtenerFiltro(idTipoNodo);
    }

    public ModuloBean obtenerPorId(ModuloBean moduloBean) throws Exception {
        return moduloDAO.obtenerPorId(moduloBean);
    }

    public TreeNode mappearModulos(List<ModuloBean> listaModuloBean) {
        TreeNode treeNode = new DefaultTreeNode(MensajesBackEnd.getValueOfKey("appSiglas", null), null);
        for (int i = 0; i < listaModuloBean.size(); i++) {
            if (listaModuloBean.get(i).getIdModuloPadre() == null) {
                TreeNode treeNodeChild = new DefaultTreeNode(listaModuloBean.get(i), treeNode);
                mappearModulosRecursive(listaModuloBean, listaModuloBean.get(i).getIdModulo(), treeNodeChild);
                treeNodeChild.setExpanded(true);
            }
        }
        return treeNode;
    }

    public TreeNode mappearModulosRecursive(List<ModuloBean> listaModuloBean, Integer idModulo, TreeNode treeNode) {
        for (int i = 0; i < listaModuloBean.size(); i++) {
            if (listaModuloBean.get(i).getIdModuloPadre() != null) {
                if (idModulo.toString().equals(listaModuloBean.get(i).getIdModuloPadre().toString())) {
                    TreeNode treeNodeChild = new DefaultTreeNode(listaModuloBean.get(i), treeNode);
//                    treeNodeChild.setExpanded(true);
                    mappearModulosRecursive(listaModuloBean, listaModuloBean.get(i).getIdModulo(), treeNodeChild);
                }
            }
        }
        if (treeNode.getChildren().isEmpty()) {
            return null;
        }
        return treeNode;
    }

    //Transacciones
    @Transactional
    public void insertar(ModuloBean moduloBean) throws Exception {
        moduloDAO.insertar(moduloBean);
    }

    @Transactional
    public void actualizar(ModuloBean moduloBean) throws Exception {
        moduloDAO.actualizar(moduloBean);
    }

    @Transactional
    public void actualizarPadre(ModuloBean moduloBean) throws Exception {
        moduloDAO.actualizarPadre(moduloBean);
    }

    @Transactional
    public void eliminarLogicamente(ModuloBean moduloBean) throws Exception {
        moduloDAO.eliminarLogicamente(moduloBean);
    }

    @Transactional
    public void eliminar(ModuloBean moduloBean) throws Exception {
        moduloDAO.eliminar(moduloBean);
        moduloDAO.eliminarHijos(moduloBean);
    }

    public List<ModuloBean> obtenerModuloPadre(Integer idModuloPadre) throws Exception {
        return moduloDAO.obtenerModuloPadre(idModuloPadre);
    }

    public List<ModuloBean> obtenerModuloPadrePosicion(Integer idModuloPadre, Integer posicionIni, Integer posicionFin, Integer idModulo) throws Exception {
        return moduloDAO.obtenerModuloPadrePosicion(idModuloPadre, posicionIni, posicionFin, idModulo);
    }

    @Transactional
    public void modificarPosicion(Integer posicion, Integer idModulo, Integer idModuloPadre) throws Exception {
        moduloDAO.modificarPosicion(posicion, idModulo, idModuloPadre);
    }

    public Integer obtenerPosMin(Integer idModuloPadre, Integer posicionIni, Integer posicionFin, Integer idModulo) throws Exception {
        return moduloDAO.obtenerPosMin(idModuloPadre, posicionIni, posicionFin, idModulo);
    }

    public Integer obtenerPosMax(Integer idModuloPadre, Integer posicionIni, Integer posicionFin, Integer idModulo) throws Exception {
        return moduloDAO.obtenerPosMax(idModuloPadre, posicionIni, posicionFin, idModulo);
    }

    @Transactional
    public void modificarOrdenamiento(ModuloBean moduloBean) throws Exception {
        moduloDAO.modificarOrdenamiento(moduloBean);
    }

    public Integer obtenerPosicionCero(Integer idModuloPadre) throws Exception {
        return moduloDAO.obtenerPosicionCero(idModuloPadre);
    }

    public List<ModuloBean> obtenerModuloPadrePos(Integer idModuloPadre) throws Exception {
        return moduloDAO.obtenerModuloPadrePos(idModuloPadre);
    }

    @Transactional
    public void modificarPosicionPro(ModuloBean moduloBean) throws Exception {
        moduloDAO.modificarPosicionPro(moduloBean);
    }

    @Transactional
    public void modificarOrdenamientoPro(ModuloBean moduloBean) throws Exception {
        moduloDAO.modificarOrdenamientoPro(moduloBean);
    }

}
