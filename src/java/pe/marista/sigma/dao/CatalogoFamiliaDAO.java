
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CatalogoFamiliaBean;


public interface CatalogoFamiliaDAO
{

    public void insertar(CatalogoFamiliaBean catalogoFamiliaBean) throws Exception;

    public void actualizar(CatalogoFamiliaBean catalogoFamiliaBean) throws Exception;

    public void eliminar(CatalogoFamiliaBean catalogoFamiliaBean) throws Exception;

    public List<CatalogoFamiliaBean> obtenerTodos() throws Exception;

    public CatalogoFamiliaBean obtenerPorId(Integer id) throws Exception;
    
}
