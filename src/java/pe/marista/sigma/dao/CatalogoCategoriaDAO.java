
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CatalogoCategoriaBean;


public interface CatalogoCategoriaDAO
{
    public void insertar(CatalogoCategoriaBean catalogoCategoriaBean) throws Exception;

    public void actualizar(CatalogoCategoriaBean catalogoCategoriaBean) throws Exception;

    public void eliminar(CatalogoCategoriaBean catalogoCategoriaBean) throws Exception;

    public List<CatalogoCategoriaBean> obtenerTodos() throws Exception;
    
    public List<CatalogoCategoriaBean> obtenerPorFamilia(Integer idCatalogoFamilia) throws Exception;

    public CatalogoCategoriaBean obtenerPorId(Integer id) throws Exception;
    
}
