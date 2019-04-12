package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.UniNegUniOrgBean;

/**
 *
 * @author Administrador
 */
public interface UniNegUniOrgDAO {
    public List<UniNegUniOrgBean> obtenerTodos() throws Exception;
    
    public List<UniNegUniOrgBean> obtenerUniOrgPorUniNeg(String uniNeg) throws Exception;
    
}
