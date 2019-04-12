/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.dao.TipoCambioDAO;

/**
 *
 * @author MS002
 */
public class TipoCambioService {
    
    private TipoCambioDAO tipoCambioDAO;

    public TipoCambioDAO getTipoCambioDAO() {
        return tipoCambioDAO;
    }

    public void setTipoCambioDAO(TipoCambioDAO tipoCambioDAO) {
        this.tipoCambioDAO = tipoCambioDAO;
    }
    public List<TipoCambioBean> obtenerTodos() throws Exception{
        return tipoCambioDAO.obtenerTodos();
    }

    public List<TipoCambioBean> obtenerTodosActivos() throws Exception{
        return tipoCambioDAO.obtenerTodosActivos();
    }
    
    public TipoCambioBean buscarPorId(TipoCambioBean tipoCambioBean) throws Exception{
        return tipoCambioDAO.buscarPorId(tipoCambioBean);
    }

    @Transactional
    public void insertar(TipoCambioBean tipoCambioBean) throws Exception{
        tipoCambioDAO.insertar(tipoCambioBean);
    }
    @Transactional
    public void actualizar(TipoCambioBean tipoCambioBean) throws Exception{
        tipoCambioDAO.actualizar(tipoCambioBean);
    }
    @Transactional
    public void eliminar(TipoCambioBean tipoCambioBean) throws Exception{
        tipoCambioDAO.eliminar(tipoCambioBean);
    }
    @Transactional
    public void cambiarEstado(TipoCambioBean tipoCambioBean) throws Exception{
        tipoCambioDAO.cambiarEstado(tipoCambioBean);
    }
    public Integer obtenerUltimoTipCambio() throws Exception{
        return tipoCambioDAO.obtenerUltimoTipCambio();
    }
    
}
