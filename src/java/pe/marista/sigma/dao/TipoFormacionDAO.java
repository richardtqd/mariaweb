
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.TipoFormacionBean;


public interface TipoFormacionDAO {

    public void insertar(TipoFormacionBean tipoFormacionBean) throws Exception;

    public void actualizar(TipoFormacionBean tipoFormacionBean) throws Exception;

    public void eliminar(TipoFormacionBean tipoFormacionBean) throws Exception;

    public List<TipoFormacionBean> obtenerTodos() throws Exception;

    public List<TipoFormacionBean> obtenerPorFiltro(TipoFormacionBean tipoFormacionBean) throws Exception;

    public TipoFormacionBean obtenerPorId(TipoFormacionBean tipoFormacionBean) throws Exception;
    
    public List<TipoFormacionBean> obtenerTipoFormacionSuperior(TipoFormacionBean tipoFormacionBean) throws Exception;
    
}
