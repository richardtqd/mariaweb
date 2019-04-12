
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.InventarioActivoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.InventarioActivoDAO;
import pe.marista.sigma.factory.BeanFactory;

public class InventarioActivoService {
    
    private InventarioActivoDAO inventarioActivoDAO;

    public InventarioActivoBean obtenerInventarioPorId(Integer idInventarioActivo) throws Exception {
        return inventarioActivoDAO.obtenerInventarioPorId(idInventarioActivo);
    }

    public void insertar(InventarioActivoBean inventarioActivoBean, CatalogoBean catalogoBean, UsuarioBean usuarioBean) throws Exception {
        CatalogoService catalogoService = BeanFactory.getCatalogoService();
        if (catalogoBean.getIdCatalogo() == null) {
            CatalogoBean catalogo = new CatalogoBean();
            catalogo.setItem(inventarioActivoBean.getCatalogoBean().getItem());
            catalogo.setPrecioRef(inventarioActivoBean.getPreciocompra());
            catalogo.getTipoUnidadMedidaBean().setIdCodigo(inventarioActivoBean.getTipoUniMedBean().getIdCodigo());
            catalogo.getTipoCategoriaBean().setIdCodigo(MaristaConstantes.Id_Categoria_Activo);
            catalogo.getTipoMonedaBean().setIdCodigo(inventarioActivoBean.getTipoMonedaBean().getIdCodigo());
            catalogo.getEntidadBean().setRuc(inventarioActivoBean.getEntidadBean().getRuc());
            catalogo.setCreaPor(usuarioBean.getUsuario());
            catalogoService.insertar(catalogo);
            inventarioActivoBean.setCatalogoBean(catalogo);
        inventarioActivoBean.getTipoCategoriaBean().setIdCodigo(MaristaConstantes.Id_Categoria_Activo);
        inventarioActivoDAO.insertar(inventarioActivoBean);
        }
    }
    
    public void actualizar(InventarioActivoBean inventarioActivoBean, CatalogoBean catalogoBean, UsuarioBean usuarioBean) throws Exception{
        inventarioActivoDAO.actualizar(inventarioActivoBean);
        CatalogoService catalogoService = BeanFactory.getCatalogoService();
        if (inventarioActivoBean.getCatalogoBean().getIdCatalogo() != null) {
            CatalogoBean catalogo = new CatalogoBean();
//            catalogo = catalogoBean;
            System.out.println(inventarioActivoBean.getCatalogoBean().getIdCatalogo());
            catalogo.setIdCatalogo(inventarioActivoBean.getCatalogoBean().getIdCatalogo());
            catalogo.setItem(inventarioActivoBean.getCatalogoBean().getItem());
            catalogo.setPrecioRef(inventarioActivoBean.getPreciocompra());
            catalogo.getTipoUnidadMedidaBean().setIdCodigo(inventarioActivoBean.getTipoUniMedBean().getIdCodigo());
            catalogo.getTipoCategoriaBean().setIdCodigo(MaristaConstantes.Id_Categoria_Activo);
            catalogo.getTipoMonedaBean().setIdCodigo(inventarioActivoBean.getTipoMonedaBean().getIdCodigo());
            catalogo.getEntidadBean().setRuc(inventarioActivoBean.getEntidadBean().getRuc());
            catalogo.setCreaPor(usuarioBean.getUsuario());
            catalogoService.actualizar(catalogo);
        }
    }

    public List<InventarioActivoBean> obtenerTodos() throws Exception {
        return inventarioActivoDAO.obtenerTodos();
    }

    public List<CatalogoBean> ObtenerIAPorItem(Integer idcatalogo) throws Exception {
        return inventarioActivoDAO.ObtenerIAPorItem(idcatalogo);
    }

    public List<CatalogoBean> ObtenerPorItem(CatalogoBean catalogoBean) throws Exception {
        return inventarioActivoDAO.ObtenerPorItem(catalogoBean);
    }    
    
    public InventarioActivoDAO getInventarioActivoDAO() {
        return inventarioActivoDAO;
    }

    public void setInventarioActivoDAO(InventarioActivoDAO inventarioActivoDAO) {
        this.inventarioActivoDAO = inventarioActivoDAO;
    }
    
     public List<InventarioActivoBean> obtenerPorFiltroInventario(InventarioActivoBean inventarioActivoBean) throws Exception {
        return inventarioActivoDAO.obtenerPorFiltroInventario(inventarioActivoBean);
    }
      public List<InventarioActivoBean> obtenerPorFiltroIA(InventarioActivoBean inventario) throws Exception {
        return inventarioActivoDAO.obtenerPorFiltroIA(inventario);
    }
        
     @Transactional
    public void modificarStockActual(InventarioActivoBean inventarioActivoBean) throws Exception {
        inventarioActivoDAO.modificarStockActual(inventarioActivoBean);
    }
    
    public List<InventarioActivoBean> obtenerPorFiltroIActivo(InventarioActivoBean activo) throws Exception {
        return inventarioActivoDAO.obtenerPorFiltroIActivo(activo);
    }
    public InventarioActivoBean obtenerPorCatalogo(InventarioActivoBean inventarioActivoBean) throws Exception {
        return inventarioActivoDAO.obtenerPorCatalogo(inventarioActivoBean);
    }
    
    public List<InventarioActivoBean> obtenerTodosActivos(String uniNeg) throws Exception {
        return inventarioActivoDAO.obtenerTodosActivos(uniNeg);
    }

    public List<InventarioActivoBean> obtenerPorFiltroItem(InventarioActivoBean inventarioActivoBean) throws Exception {
        return inventarioActivoDAO.obtenerPorFiltroItem(inventarioActivoBean);
    }
    
    
}
