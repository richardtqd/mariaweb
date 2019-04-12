package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CatalogoFamiliaBean;
import pe.marista.sigma.bean.InventarioActivoBean;
import pe.marista.sigma.bean.InventarioAlmacenBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.CatalogoDAO;
import pe.marista.sigma.dao.InventarioActivoDAO;
import pe.marista.sigma.dao.InventarioAlmacenDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;

public class CatalogoService {

    private CatalogoDAO catalogoDAO;
    private InventarioAlmacenDAO inventarioAlmacenDAO;
    private InventarioActivoDAO inventarioActivoDAO;

    //Getter and Setter
    public CatalogoDAO getCatalogoDAO() {
        return catalogoDAO;
    }

    public void setCatalogoDAO(CatalogoDAO catalogoDAO) {
        this.catalogoDAO = catalogoDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(CatalogoBean catalogoBean, InventarioAlmacenBean inventarioAlmacenBean, InventarioActivoBean inventarioActivoBean, UsuarioBean usuarioBean) throws Exception {
        catalogoDAO.insertar(catalogoBean);
//        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18201) {
//            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            InventarioAlmacenBean inventarioAlmacen = new InventarioAlmacenBean();
            inventarioAlmacen.setCatalogoBean(catalogoBean);
            inventarioAlmacen.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            inventarioAlmacen.setStockActual(inventarioAlmacenBean.getStockActual());
            inventarioAlmacen.setStockMin(inventarioAlmacenBean.getStockMin());
            inventarioAlmacen.getTipoUniMedBean().setIdCodigo(catalogoBean.getTipoUnidadMedidaBean().getIdCodigo());
            inventarioAlmacen.setPrecioRef(catalogoBean.getPrecioRef());
            inventarioAlmacen.setCreaPor(usuarioBean.getUsuario());
            inventarioAlmacen.setNroAlmacen(1);
            inventarioAlmacenDAO.insertar(inventarioAlmacen);
        }
        if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18202) {
//            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            inventarioActivoBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            inventarioActivoBean.setCreapor(usuarioBean.getUsuario());
            inventarioActivoBean.setMarca(inventarioActivoBean.getMarca());
            inventarioActivoBean.getEntidadBean().setRuc(catalogoBean.getEntidadBean().getRuc());
            inventarioActivoBean.getTipoUniMedBean().setIdCodigo(catalogoBean.getTipoUnidadMedidaBean().getIdCodigo());
            inventarioActivoBean.getTipoMonedaBean().setIdCodigo(catalogoBean.getTipoMonedaBean().getIdCodigo());
            inventarioActivoBean.getTipoCategoriaBean().setIdCodigo(catalogoBean.getTipoCategoriaBean().getIdCodigo());
            inventarioActivoBean.getCatalogoBean().setIdCatalogo(catalogoBean.getIdCatalogo());
            inventarioActivoBean.setModelo(inventarioActivoBean.getModelo());
            inventarioActivoBean.setPreciocompra(catalogoBean.getPrecioRef());
            inventarioActivoDAO.insertar(inventarioActivoBean);
        }
    }

    @Transactional
    public void actualizar(CatalogoBean catalogoBean, InventarioAlmacenBean inventarioAlmacenBean, InventarioActivoBean inventarioActivoBean, UsuarioBean usuarioBean) throws Exception {
        catalogoDAO.actualizar(catalogoBean);
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18201) {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            inventarioAlmacenBean.setCatalogoBean(catalogoBean);
            inventarioAlmacenBean.setStockActual(inventarioAlmacenBean.getStockActual());
            inventarioAlmacenBean.setStockMin(inventarioAlmacenBean.getStockMin());
            inventarioAlmacenBean.getTipoUniMedBean().setIdCodigo(catalogoBean.getTipoUnidadMedidaBean().getIdCodigo());
            inventarioAlmacenBean.setPrecioRef(catalogoBean.getPrecioRef());
            inventarioAlmacenBean.setModiPor(beanUsuarioSesion.getUsuario());
            inventarioAlmacenBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            inventarioAlmacenBean.getCatalogoBean().setIdCatalogo(catalogoBean.getIdCatalogo());
            inventarioAlmacenDAO.actualizar(inventarioAlmacenBean);
        }
        if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18202) {
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            inventarioActivoBean.setCatalogoBean(catalogoBean);
            inventarioActivoBean.setModiPor(beanUsuarioSesion.getUsuario());
            inventarioActivoBean.setMarca(inventarioActivoBean.getMarca());
            inventarioActivoBean.getEntidadBean().setRuc(catalogoBean.getEntidadBean().getRuc());
            inventarioActivoBean.getTipoUniMedBean().setIdCodigo(catalogoBean.getTipoUnidadMedidaBean().getIdCodigo());
            inventarioActivoBean.getTipoMonedaBean().setIdCodigo(catalogoBean.getTipoMonedaBean().getIdCodigo());
            inventarioActivoBean.getTipoCategoriaBean().setIdCodigo(catalogoBean.getTipoCategoriaBean().getIdCodigo());
            inventarioActivoBean.getCatalogoBean().setIdCatalogo(catalogoBean.getIdCatalogo());
            inventarioActivoBean.setModelo(inventarioActivoBean.getModelo());
            inventarioActivoBean.setPreciocompra(catalogoBean.getPrecioRef());
            inventarioActivoBean.setStockActual(inventarioActivoBean.getStockActual());
            inventarioActivoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            inventarioActivoBean.setIdInventarioActivo(catalogoBean.getInventarioActivoBean().getIdInventarioActivo());
            inventarioActivoDAO.actualizar(inventarioActivoBean);
        }
    }

    @Transactional
    public void actualizarPrecioRef(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.actualizarPrecioRef(catalogoBean);

    }

    @Transactional
    public void actualizar(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.actualizar(catalogoBean);

    }

    @Transactional
    public void insertar(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.insertar(catalogoBean);

    }

    @Transactional
    public void eliminar(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.eliminar(catalogoBean);
    }

    public List<CatalogoBean> obtenerTodos() throws Exception {
        return catalogoDAO.obtenerTodos();
    }

    public List<CatalogoBean> obtenerMateriales(String uniNeg) throws Exception {
        return catalogoDAO.obtenerMateriales(uniNeg);
    }

    public List<CatalogoBean> obtenerActivos(String uniNeg) throws Exception {
        return catalogoDAO.obtenerActivos(uniNeg);
    }

    public List<CatalogoBean> obtenerServicios() throws Exception {
        return catalogoDAO.obtenerServicios();
    }

    public List<CatalogoBean> obtenerPorFiltro(CatalogoBean catalogoBean) throws Exception {
        return catalogoDAO.obtenerPorFiltro(catalogoBean);
    }

    public CatalogoBean obtenerPorId(CatalogoBean catalogoBean) throws Exception {
        return catalogoDAO.obtenerPorId(catalogoBean);
    }
 //   public CatalogoFamiliaBean obtenerCatalogoFamiliaPorNombre(String nombre) throws Exception{    
    //   return catalogoDAO.obtenerCatalogoFamiliaPorNombre(nombre); 
    //   }

    //     public CatalogoBean buscarPorId(String item) throws Exception {
    //     return catalogoDAO.buscarPorId(item);
    // }
    public CatalogoBean obtenerCatalogoPorNombre(String nombre) throws Exception {
        return catalogoDAO.obtenerCatalogoPorNombre(nombre);
    }

//    public List<CatalogoBean> obtenerPorFiltroCat(CatalogoBean catalogoBean) throws Exception {
//        return catalogoDAO.obtenerPorFiltroCat(catalogoBean);
//    } 
    public List<CatalogoBean> obtenerPorFiltroCat(CatalogoBean catalogoBean) throws Exception {
        return catalogoDAO.obtenerPorFiltro(catalogoBean);
    }

    //AUMENTE
    public CatalogoBean obtenerPorCatalogo(CatalogoBean catalogoBean) throws Exception {
        return catalogoDAO.obtenerPorCatalogo(catalogoBean);
    }

    public InventarioAlmacenDAO getInventarioAlmacenDAO() {
        return inventarioAlmacenDAO;
    }

    public void setInventarioAlmacenDAO(InventarioAlmacenDAO inventarioAlmacenDAO) {
        this.inventarioAlmacenDAO = inventarioAlmacenDAO;
    }

    public InventarioActivoDAO getInventarioActivoDAO() {
        return inventarioActivoDAO;
    }

    public void setInventarioActivoDAO(InventarioActivoDAO inventarioActivoDAO) {
        this.inventarioActivoDAO = inventarioActivoDAO;
    }

    public CatalogoBean obtenerCatalogoPorId(CatalogoBean catalogoBean) throws Exception {
        return catalogoDAO.obtenerCatalogoPorId(catalogoBean);
    }

    public void modificarItemDetalleOrdenCompra(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.modificarItemDetalleOrdenCompra(catalogoBean);
    }

    public List<CatalogoBean> obtenerCatalogoPorAlmacen(String uniNeg) throws Exception {
        return catalogoDAO.obtenerCatalogoPorAlmacen(uniNeg);
    }

    @Transactional
    public void modificarPorEntidad(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.modificarPorEntidad(catalogoBean);
    }

    public Integer obtenerUltimoCatalogo(String uniNeg) throws Exception {
        return catalogoDAO.obtenerUltimoCatalogo(uniNeg);
    }

    @Transactional
    public void execProInventarioCarga(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.execProInventarioCarga(catalogoBean);
    }

    public List<CatalogoBean> obtenerPorFiltroCategoria(CatalogoBean catalogoBean) throws Exception {
        return catalogoDAO.obtenerPorFiltroCategoria(catalogoBean);
    }

    public void modificarItemBlockDetalleOrdenCompra(CatalogoBean catalogoBean) throws Exception {
        catalogoDAO.modificarItemBlockDetalleOrdenCompra(catalogoBean);
    }

    
}
