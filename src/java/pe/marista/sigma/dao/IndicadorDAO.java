/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.IndicadorBean;

/**
 *
 * @author MS002
 */
public interface IndicadorDAO {

    public List<IndicadorBean> obtenerTodos() throws Exception; 
    
    public List<IndicadorBean> obtenerPorTipoUso(@Param("idCodigo")Integer idCodigo) throws Exception; 
    
    public List<IndicadorBean> obtenerPorFiltro(IndicadorBean indicadorBean) throws Exception;

    public IndicadorBean buscarPorId(Integer idIndicador) throws Exception;

    public void insertar(IndicadorBean indicadorBean) throws Exception;

    public void actualizar(IndicadorBean indicadorBean) throws Exception;

    public void eliminar(Integer idIndicador) throws Exception;
    
    public String obtenerCodigo(String codigo) throws Exception;
    
}
