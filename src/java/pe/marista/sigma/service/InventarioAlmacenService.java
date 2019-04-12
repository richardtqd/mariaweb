package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.InventarioActivoBean;
import pe.marista.sigma.bean.InventarioAlmacenBean;
import pe.marista.sigma.dao.InventarioAlmacenDAO;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.MovimientoActivoBean;
import pe.marista.sigma.bean.MovimientoAlmacenBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.InventarioAlmacenGeneralRepBean;
import pe.marista.sigma.bean.reporte.MovimientoAlmacenGeneralRepBean;
import pe.marista.sigma.bean.reporte.MovimientoAlmacenRepBean;
import pe.marista.sigma.dao.CatalogoDAO;
import pe.marista.sigma.factory.BeanFactory;

public class InventarioAlmacenService {

    private InventarioAlmacenDAO inventarioAlmacenDAO;
    private CatalogoDAO catalogoDAO;

    //Getter and Setter
    public InventarioAlmacenDAO getInventarioAlmacenDAO() {
        return inventarioAlmacenDAO;
    }

    public List<MovimientoAlmacenRepBean> obtenerUltimoMovPDF2(Integer nroMovimiento, String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerUltimoMovPDF2(nroMovimiento, uniNeg);
    }

    public void setInventarioAlmacenDAO(InventarioAlmacenDAO inventarioAlmacenDAO) {
        this.inventarioAlmacenDAO = inventarioAlmacenDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(InventarioAlmacenBean inventarioAlmacenBean, CatalogoBean catalogoBean, UsuarioBean usuarioBean) throws Exception {
        CatalogoService catalogoService = BeanFactory.getCatalogoService();
        if (inventarioAlmacenBean.getCatalogoBean().getIdCatalogo() == null) {
            CatalogoBean catalogo = new CatalogoBean();
            catalogo.setItem(inventarioAlmacenBean.getCatalogoBean().getItem());
            catalogo.setPrecioRef(inventarioAlmacenBean.getPrecioRef());
            catalogo.getTipoUnidadMedidaBean().setIdCodigo(inventarioAlmacenBean.getTipoUniMedBean().getIdCodigo());
            catalogo.getTipoCategoriaBean().setIdCodigo(MaristaConstantes.Id_Categoria_Almacen);
            catalogo.getTipoMonedaBean().setIdCodigo(inventarioAlmacenBean.getCatalogoBean().getTipoMonedaBean().getIdCodigo());
            catalogo.getEntidadBean().setRuc(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc());
            catalogo.setCreaPor(usuarioBean.getUsuario());
            catalogoService.insertar(catalogo);
            inventarioAlmacenBean.setCatalogoBean(catalogo);
            inventarioAlmacenDAO.insertar(inventarioAlmacenBean);
        } else {
            inventarioAlmacenDAO.insertar(inventarioAlmacenBean);
        }
    }

    @Transactional
    public void actualizar(InventarioAlmacenBean inventarioAlmacenBean, CatalogoBean catalogoBean, UsuarioBean usuarioBean) throws Exception {
        inventarioAlmacenDAO.actualizar(inventarioAlmacenBean);
        CatalogoService catalogoService = BeanFactory.getCatalogoService();
        if (inventarioAlmacenBean.getCatalogoBean().getIdCatalogo() != null) {
            System.out.println("if (inventarioAlmacenBean.getCatalogoBean().getIdCatalogo() != null)");
            CatalogoBean catalogo = new CatalogoBean();
//            catalogo = catalogoBean;
            System.out.println(inventarioAlmacenBean.getCatalogoBean().getIdCatalogo());
            catalogo.setIdCatalogo(inventarioAlmacenBean.getCatalogoBean().getIdCatalogo());
            catalogo.setItem(inventarioAlmacenBean.getCatalogoBean().getItem());
            catalogo.setPrecioRef(inventarioAlmacenBean.getPrecioRef());
            catalogo.getTipoUnidadMedidaBean().setIdCodigo(inventarioAlmacenBean.getTipoUniMedBean().getIdCodigo());
            catalogo.getTipoCategoriaBean().setIdCodigo(MaristaConstantes.Id_Categoria_Almacen);
            catalogo.getTipoMonedaBean().setIdCodigo(inventarioAlmacenBean.getCatalogoBean().getTipoMonedaBean().getIdCodigo());
            catalogo.getEntidadBean().setRuc(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc());
            catalogo.setCreaPor(usuarioBean.getUsuario());
            catalogoService.actualizar(catalogo);
        }
    }

    public List<InventarioAlmacenBean> obtenerTodosInventario() throws Exception {
        return inventarioAlmacenDAO.obtenerTodosInventario();
    }

    public InventarioAlmacenBean obtenerPorId(Integer idAlmacen) throws Exception {
        return inventarioAlmacenDAO.obtenerPorId(idAlmacen);
    }

    public InventarioAlmacenBean obtenerPorCatalogo(InventarioAlmacenBean inventarioAlmacenBean) throws Exception {
        return inventarioAlmacenDAO.obtenerPorCatalogo(inventarioAlmacenBean);
    }

    public List<InventarioAlmacenBean> obtenerPorFiltroIAlmacen(InventarioAlmacenBean almacen) throws Exception {
        return inventarioAlmacenDAO.obtenerPorFiltroIAlmacen(almacen);
    }

    public List<InventarioAlmacenBean> obtenerTodos(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerTodos(uniNeg);
    }

    @Transactional
    public void modificarStockActual(InventarioAlmacenBean inventarioAlmacenBean) throws Exception {
        inventarioAlmacenDAO.modificarStockActual(inventarioAlmacenBean);
    }

    @Transactional
    public void modificarStockActualMov(InventarioAlmacenBean inventarioAlmacenBean) throws Exception {
        inventarioAlmacenDAO.modificarStockActualMov(inventarioAlmacenBean);
    }

    public Integer obtenerUltimoMov(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerUltimoMov(uniNeg);
    }

    public String obtenerUltimo(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerUltimo(uniNeg);
    }

    @Transactional
    public void insertarMovimiento(MovimientoAlmacenBean movimientoAlmacenBean, List<MovimientoAlmacenBean> listaMovimientoAlmacen, UsuarioBean usuarioBean) throws Exception {
        InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
        Integer ID = inventarioAlmacenService.obtenerUltimoMov(movimientoAlmacenBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        movimientoAlmacenBean.setNroMovimiento(ID);
        for (MovimientoAlmacenBean lista : listaMovimientoAlmacen) {
            MovimientoAlmacenBean movi = new MovimientoAlmacenBean();
            movi = movimientoAlmacenBean;
            lista.getUnidadNegocioBean().setUniNeg(movi.getUnidadNegocioBean().getUniNeg());
            lista.getInventarioAlmacenBean().setUnidadNegocioBean(movi.getUnidadNegocioBean());
            lista.setEntregadoPor(movi.getEntregadoPor());
            lista.setFechaMov(movi.getFechaMov());
            lista.setRecibidopor(movi.getRecibidoPor());
            lista.setCreaPor(movi.getCreaPor());
            lista.setNroMovimiento(movimientoAlmacenBean.getNroMovimiento());
            lista.getTipoUniMedBean().setIdCodigo(lista.getCatalogoBean().getTipoUnidadMedidaBean().getIdCodigo());
            lista.getInventarioAlmacenBean().setCatalogoBean(lista.getCatalogoBean());
            lista.getInventarioAlmacenBean().setIdAlmacen(movi.getInventarioAlmacenBean().getIdAlmacen());

            inventarioAlmacenDAO.insertarMovimiento(lista);
            inventarioAlmacenService.modificarStockActualMov(lista.getInventarioAlmacenBean());
        }
    }

    @Transactional
    public void insertarMovimiento(MovimientoAlmacenBean movimientoAlmacenBean) throws Exception {
        inventarioAlmacenDAO.insertarMovimiento(movimientoAlmacenBean);

    }

    public Integer obtenerUltimoPDF(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerUltimoPDF(uniNeg);
    }

    public List<MovimientoAlmacenBean> obtenerUltimoMovPDF(Integer nroMovimiento, String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerUltimoMovPDF(nroMovimiento, uniNeg);
    }

    public List<MovimientoAlmacenBean> obtenerTodosMovi(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerTodosMovi(uniNeg);
    }

    public void eliminar(MovimientoAlmacenBean movimientoAlmacenBean) throws Exception {
        inventarioAlmacenDAO.eliminar(movimientoAlmacenBean);
    }

    public List<InventarioAlmacenBean> obtenerColorAlmacen(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerColorAlmacen(uniNeg);
    }

    public InventarioAlmacenBean obtenerColorAlmacenPorNro(String uniNeg, Integer nroAlmacen) throws Exception {
        return inventarioAlmacenDAO.obtenerColorAlmacenPorNro(uniNeg, nroAlmacen);
    }

    public List<InventarioAlmacenGeneralRepBean> obtenerTodosReporte(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerTodosReporte(uniNeg);
    }

    public List<InventarioAlmacenBean> obtenerAlmacenPorNro(InventarioAlmacenBean inventarioAlmacenBean) throws Exception {
        return inventarioAlmacenDAO.obtenerAlmacenPorNro(inventarioAlmacenBean);
    }

    public void eliminarInvAlmacen(Integer idAlmacen) throws Exception {
        inventarioAlmacenDAO.eliminarInvAlmacen(idAlmacen);
    }

    public List<MovimientoAlmacenRepBean> obtenerReporteMovAlmacen(Integer nroMovimiento, String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerReporteMovAlmacen(nroMovimiento, uniNeg);
    }

    public List<MovimientoAlmacenGeneralRepBean> obtenerTodosReporteMov(String uniNeg) throws Exception {
        return inventarioAlmacenDAO.obtenerTodosReporteMov(uniNeg);
    }

    
}
