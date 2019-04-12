/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocIngresoSerieBean;

/**
 *
 * @author MS002
 */
public interface DocIngresoSerieDAO {

    public List<DocIngresoSerieBean> obtenerTodos(String uniNeg) throws Exception;

    public List<DocIngresoSerieBean> obtenerTodosActivos(String uniNeg) throws Exception;
    
    public DocIngresoSerieBean buscarPorId(DocIngresoSerieBean docIngresoSerieBean) throws Exception;

    public void insertar(DocIngresoSerieBean docIngresoSerieBean) throws Exception;

    public void actualizar(DocIngresoSerieBean docIngresoSerieBean) throws Exception;

    public void eliminar(DocIngresoSerieBean docIngresoSerieBean) throws Exception;

    public void cambiarEstado(DocIngresoSerieBean docIngresoSerieBean) throws Exception;
    
    public DocIngresoSerieBean obtenerActual() throws Exception;

}
