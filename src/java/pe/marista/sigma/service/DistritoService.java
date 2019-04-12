

package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.DepartamentoBean;
import pe.marista.sigma.bean.DistritoBean;
import pe.marista.sigma.bean.ProvinciaBean;
import pe.marista.sigma.dao.DistritoDAO;

/**
 *
 * @author Administrador
 */
public class DistritoService{
    private DistritoDAO distritoDAO;

    public DistritoBean obtenerDistritoPorId(Integer idDistrito) throws Exception {
        return distritoDAO.obtenerDistritoPorId(idDistrito);
    }
    public List<DepartamentoBean> obtenerDepartamentos() throws Exception {
        return distritoDAO.obtenerDepartamentos();
    }
    public List<ProvinciaBean> obtenerProvinciaPorDep(DepartamentoBean departamentoBean) throws Exception {
        return distritoDAO.obtenerProvinciaPorDep(departamentoBean);
    }
    public List<DistritoBean> obtenerDistritoPorProv(ProvinciaBean provinciaBean) throws Exception {
        return distritoDAO.obtenerDistritoPorProv(provinciaBean);
    }

    public DistritoDAO getDistritoDAO() {
        return distritoDAO;
    }

    public void setDistritoDAO(DistritoDAO distritoDAO) {
        this.distritoDAO = distritoDAO;
    }
    
}
