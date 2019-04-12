package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CatalogoFamiliaBean;

public interface CatalogoDAO {

    public void insertar(CatalogoBean catalogoBean) throws Exception;

    public void actualizar(CatalogoBean catalogoBean) throws Exception;

    public void actualizarPrecioRef(CatalogoBean catalogoBean) throws Exception;

    public void modificarItemDetalleOrdenCompra(CatalogoBean catalogoBean) throws Exception;
    
    public void modificarItemBlockDetalleOrdenCompra(CatalogoBean catalogoBean) throws Exception;

    public void eliminar(CatalogoBean catalogoBean) throws Exception;

    public List<CatalogoBean> obtenerTodos() throws Exception;

    public List<CatalogoBean> obtenerMateriales(String uniNeg) throws Exception;

    public List<CatalogoBean> obtenerActivos(String uniNeg) throws Exception;

    public List<CatalogoBean> obtenerServicios() throws Exception;

    public List<CatalogoBean> obtenerPorFiltro(CatalogoBean catalogoBean) throws Exception;

    public CatalogoBean obtenerPorId(CatalogoBean catalogoBean) throws Exception;

    public List<CatalogoBean> obtenerPorFiltroCat(CatalogoBean catalogoBean) throws Exception;

    //public CatalogoFamiliaBean obtenerCatalogoFamiliaPorNombre(String nombre) throws Exception;
    public CatalogoBean obtenerCatalogoPorNombre(String nombre) throws Exception;

    public CatalogoBean buscarPorId(Integer idCatalogo) throws Exception;

    public CatalogoBean obtenerPorCatalogo(CatalogoBean catalogoBean) throws Exception; //AUMENTE

    public CatalogoBean obtenerCatalogoPorId(CatalogoBean catalogoBean) throws Exception;

    public List<CatalogoBean> obtenerCatalogoPorAlmacen(String uniNeg) throws Exception; //ALMACEN POR INVENTARIO

    public void modificarPorEntidad(CatalogoBean catalogoBean) throws Exception;

    public Integer obtenerUltimoCatalogo(String uniNeg) throws Exception;//OBTENER MAXIMO NRO. CATALOGO

    public void execProInventarioCarga(CatalogoBean catalogoBean) throws Exception;

    public List<CatalogoBean> obtenerPorFiltroCategoria(CatalogoBean catalogoBean) throws Exception;

}
