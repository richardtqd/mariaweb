/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PlanEstrategicoBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;

/**
 *
 * @author MS002
 */
public interface PlanEstrategicoDAO {

    public List<PlanEstrategicoBean> obtenerTodos() throws Exception;

    public List<PlanEstrategicoBean> obtenerPorFiltro(PlanEstrategicoBean planEstrategicoBean) throws Exception;

    public PlanEstrategicoBean buscarPorId(Integer idPlanEstrategico) throws Exception;

    public void insertar(PlanEstrategicoBean planEstrategicoBean) throws Exception;

    public void actualizar(PlanEstrategicoBean planEstrategicoBean) throws Exception;

    public void eliminar(Integer idPlanEstrategico) throws Exception;

    public List<UnidadOrganicaBean> obtenerUnidadOrg() throws Exception;

    public String obtenerUltimoCodigo(PlanEstrategicoBean planEstrategicoBean) throws Exception;

    public PlanEstrategicoBean obtenerPorId(PlanEstrategicoBean planEstrategicoBean) throws Exception;

    public PlanEstrategicoBean obtenerPlanId(Integer idPlanEstrategico) throws Exception;

    public List<PlanEstrategicoBean> obtenerTodosUniNeg(@Param("uniNeg") String uniNeg) throws Exception;

}
