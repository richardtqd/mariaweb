/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.VistaBean;

/**
 *
 * @author Administrador
 */
public interface VistaDAO {

    public List<VistaBean> obtenerTodos() throws Exception;

    public List<VistaBean> obtenerPorUsuario(VistaBean vistaBean) throws Exception;

    public void insertar(VistaBean vistaBean) throws Exception;

    public void eliminar(VistaBean vistaBean) throws Exception;
}
