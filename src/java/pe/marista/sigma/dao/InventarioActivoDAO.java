
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.InventarioActivoBean;

public interface InventarioActivoDAO {

    public InventarioActivoBean obtenerInventarioPorId(Integer idInventarioActivo) throws Exception;
    
    public void insertar (InventarioActivoBean inventarioActivoBean) throws Exception;
    
    public void actualizar (InventarioActivoBean inventarioActivoBean) throws Exception;
    
    public List<InventarioActivoBean> obtenerTodos() throws Exception;
    
    public List<CatalogoBean> ObtenerIAPorItem(Integer idcatalogo) throws Exception;
    
    public List<CatalogoBean> ObtenerPorItem(CatalogoBean catalogoBean) throws Exception;
    
    public List<InventarioActivoBean> obtenerPorFiltroInventario(InventarioActivoBean inventarioActivoBean) throws Exception;
        
    public List<InventarioActivoBean> obtenerPorFiltroIA(InventarioActivoBean inventario) throws Exception;
    
    public void modificarStockActual(InventarioActivoBean inventarioActivoBean) throws Exception;
    
    public List<InventarioActivoBean> obtenerPorFiltroIActivo(InventarioActivoBean activo) throws Exception;
    
    public InventarioActivoBean obtenerPorCatalogo(InventarioActivoBean inventarioActivoBean) throws Exception;
    
    public List<InventarioActivoBean> obtenerTodosActivos(String uniNeg) throws Exception;
    
    public List<InventarioActivoBean> obtenerPorFiltroItem(InventarioActivoBean inventarioActivoBean) throws Exception;
}
