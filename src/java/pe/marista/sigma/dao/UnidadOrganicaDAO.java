package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.UnidadOrganicaBean;

public interface UnidadOrganicaDAO {

    public List<UnidadOrganicaBean> obtenerTodos() throws Exception;

    public UnidadOrganicaBean obtenerUnidadOrganicaPorId(UnidadOrganicaBean unidadOrganica) throws Exception;
 //   public List<UnidadOrganicaBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception;

}
