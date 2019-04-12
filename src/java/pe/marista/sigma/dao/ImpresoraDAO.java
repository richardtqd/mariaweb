/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ImpresoraBean;

/**
 *
 * @author MS002
 */
public interface ImpresoraDAO {

    public List<ImpresoraBean> obtenerTodos(String uniNeg) throws Exception;

    public List<ImpresoraBean> obtenerTodosActivos(String uniNeg) throws Exception;

    public ImpresoraBean buscarPorId(ImpresoraBean impresoraBean) throws Exception;

    public void insertar(ImpresoraBean impresoraBean) throws Exception;

    public void actualizar(ImpresoraBean impresoraBean) throws Exception;

    public void eliminar(ImpresoraBean impresoraBean) throws Exception;

    public void cambiarEstado(ImpresoraBean impresoraBean) throws Exception;
    
    public void cambiarNro(ImpresoraBean impresoraBean) throws Exception;

    public ImpresoraBean obtenerActual() throws Exception;

    public List<ImpresoraBean> obtenerTodosTipoDoc(ImpresoraBean impresoraBean) throws Exception;

    public List<ImpresoraBean> obtenerGrupoImpresoraActivos(String uniNeg) throws Exception;

    public List<ImpresoraBean> obtenerImpresoraPrincipal(String uniNeg) throws Exception;

    public ImpresoraBean obtenerPorNombre(@Param("uniNeg") String uniNeg, @Param("impresora") String impresora) throws Exception;
    
    public List<ImpresoraBean> obtenerCuotaIngre() throws Exception;
    
    public List<ImpresoraBean> obtenerImpPensiones() throws Exception;

}
