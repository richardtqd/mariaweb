/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.LineaEstrategicaBean;

/**
 *
 * @author MS002
 */
public interface LineaEstrategicaDAO {

    public List<LineaEstrategicaBean> obtenerLineaEstrategica() throws Exception;

    public LineaEstrategicaBean obtenerPorId(Integer idLinea) throws Exception;

    public List<LineaEstrategicaBean> obtenerListaPorId(@Param("idLinea") Integer idLinea, @Param("uniNeg") String uniNeg) throws Exception;

    public List<LineaEstrategicaBean> obtenerLineaPorPlan(@Param("idPlanEstrategico") Integer idPlanEstrategico, @Param("uniNeg") String uniNeg) throws Exception;

    public void insertarLineaEstrategica(LineaEstrategicaBean lineaEstrategicaBean) throws Exception;

    public void actualizarLineaEstrategica(LineaEstrategicaBean lineaEstrategicaBean) throws Exception;

    public void eliminarLineaEstrategica(Integer idLinea) throws Exception;

    public String obtenerUltimoCodigo(String uniNeg) throws Exception;

    public void cambiarEstado(LineaEstrategicaBean lineaEstrategicaBean) throws Exception;

    public List<LineaEstrategicaBean> obtenerLineaPorPlanEstrategico(@Param("idPlanEstrategico") Integer idPlanEstrategico, @Param("uniNeg") String uniNeg) throws Exception;
}
