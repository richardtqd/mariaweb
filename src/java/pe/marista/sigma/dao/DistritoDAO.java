package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.DepartamentoBean;
import pe.marista.sigma.bean.DistritoBean;
import pe.marista.sigma.bean.ProvinciaBean;

/**
 *
 * @author Administrador
 */
public interface DistritoDAO {

    public DistritoBean obtenerDistritoPorId(Integer idDistrito) throws Exception;

    public List<DepartamentoBean> obtenerDepartamentos() throws Exception;

    public List<ProvinciaBean> obtenerProvinciaPorDep(DepartamentoBean departamentoBean) throws Exception;

    public List<DistritoBean> obtenerDistritoPorProv(ProvinciaBean provinciaBean) throws Exception;
}
