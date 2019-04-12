/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.dao.ImpresoraDAO;

/**
 *
 * @author MS002
 */
public class ImpresoraService {
    //Variables

    private ImpresoraDAO impresoraDAO;

    public ImpresoraDAO getImpresoraDAO() {
        return impresoraDAO;
    }

    public void setImpresoraDAO(ImpresoraDAO impresoraDAO) {
        this.impresoraDAO = impresoraDAO;
    }

    public List<ImpresoraBean> obtenerTodos(String uniNeg) throws Exception {
        return impresoraDAO.obtenerTodos(uniNeg);
    }

    public List<ImpresoraBean> obtenerTodosActivos(String uniNeg) throws Exception {
        return impresoraDAO.obtenerTodosActivos(uniNeg);
    }

    public ImpresoraBean buscarPorId(ImpresoraBean impresoraBean) throws Exception {
        return impresoraDAO.buscarPorId(impresoraBean);
    }

    @Transactional
    public void insertar(ImpresoraBean impresoraBean) throws Exception {
        impresoraDAO.insertar(impresoraBean);
    }

    @Transactional
    public void actualizar(ImpresoraBean impresoraBean) throws Exception {
        impresoraDAO.actualizar(impresoraBean);
    }

    @Transactional
    public void eliminar(ImpresoraBean impresoraBean) throws Exception {
        impresoraDAO.eliminar(impresoraBean);
    }

    @Transactional
    public void cambiarEstado(ImpresoraBean impresoraBean) throws Exception {
        impresoraDAO.cambiarEstado(impresoraBean);
    }

    public ImpresoraBean obtenerPorNombre(String uniNeg, String impresora) throws Exception {
        return impresoraDAO.obtenerPorNombre(uniNeg, impresora);
    } 

    public ImpresoraBean obtenerActual() throws Exception {
        return impresoraDAO.obtenerActual();
    }

    public List<ImpresoraBean> obtenerGrupoImpresoraActivos(String uniNeg) throws Exception {
        return impresoraDAO.obtenerGrupoImpresoraActivos(uniNeg);
    }

    public List<ImpresoraBean> obtenerImpresoraPrincipal(String uniNeg) throws Exception {
        return impresoraDAO.obtenerImpresoraPrincipal(uniNeg);
    }

    public List<ImpresoraBean> obtenerTodosTipoDoc(ImpresoraBean impresoraBean) throws Exception {
        return impresoraDAO.obtenerTodosTipoDoc(impresoraBean);
    }

    public void cambiarNro(ImpresoraBean impresoraBean) throws Exception {
        impresoraDAO.cambiarNro(impresoraBean);
    }

    public List<ImpresoraBean> obtenerCuotaIngre() throws Exception {
        return impresoraDAO.obtenerCuotaIngre();
    }

    public List<ImpresoraBean> obtenerImpPensiones() throws Exception {
        return impresoraDAO.obtenerImpPensiones();
    }
}
