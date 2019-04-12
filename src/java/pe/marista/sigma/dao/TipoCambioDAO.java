/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.TipoCambioBean;

/**
 *
 * @author MS002
 */
public interface TipoCambioDAO {
    
    public List<TipoCambioBean> obtenerTodos() throws Exception;

    public List<TipoCambioBean> obtenerTodosActivos() throws Exception;
    
    public TipoCambioBean buscarPorId(TipoCambioBean tipoCambioBean) throws Exception;

    public void insertar(TipoCambioBean tipoCambioBean) throws Exception;

    public void actualizar(TipoCambioBean tipoCambioBean) throws Exception;

    public void eliminar(TipoCambioBean tipoCambioBean) throws Exception;

    public void cambiarEstado(TipoCambioBean tipoCambioBean) throws Exception;
    
    public Integer obtenerUltimoTipCambio() throws Exception;
}
