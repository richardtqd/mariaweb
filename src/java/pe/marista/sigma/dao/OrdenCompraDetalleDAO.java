package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.reporte.ContratoAdquisicionRepBean;
import pe.marista.sigma.bean.reporte.ContratoRepBean;
import pe.marista.sigma.bean.reporte.DetContratoRepBean;
import pe.marista.sigma.bean.reporte.DetOrdenCompraRepBean;
import pe.marista.sigma.bean.reporte.DetContratoAdquisicionRepBean;

public interface OrdenCompraDetalleDAO {

    public List<OrdenCompraDetalleBean> obtenerPorOrden(@Param("idOrdenCompra") Integer idOrdenCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public List<OrdenCompraDetalleBean> obtenerPorOrdenFiltro(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception;

    public Integer obtenerUltimo(@Param("idOrdenCompra") Integer idOrdenCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public List<OrdenCompraDetalleBean> obtenerListaPorId(Integer idDetOrdenCompra) throws Exception;

    public List<OrdenCompraDetalleBean> obtenerTodos(String uniNeg) throws Exception;

    public void insertar(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception;

    public void insertarCotizacionOrden(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception;

    public void eliminar(OrdenCompraBean ordenCompraBean) throws Exception;

    public void modificarFactura(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception;

    public void validarRecepcionCompra(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception;

    public OrdenCompraDetalleBean obtenerPorId(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception;

    public List<OrdenCompraDetalleBean> obtenerTodosProveedores(OrdenCompraDetalleBean bean) throws Exception;

    public List<OrdenCompraDetalleBean> obtenerTodosItems(OrdenCompraDetalleBean bean) throws Exception;

    public List<DetOrdenCompraRepBean> obtenerOrdenCompra(@Param("idOrdenCompra") Integer idOrdenCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ContratoRepBean> obtenerContrato(@Param("ruc") String ruc, @Param("uniNeg") String uniNeg, @Param("idOrdenCompra") Integer idOrdenCompra) throws Exception;

    public List<DetContratoRepBean> obtenerDetContrato(@Param("uniNeg") String uniNeg, @Param("idOrdenCompra") Integer idOrdenCompra) throws Exception;

    public List<ContratoAdquisicionRepBean> obtenerContratoAdqui(@Param("ruc") String ruc, @Param("uniNeg") String uniNeg, @Param("idOrdenCompra") Integer idOrdenCompra) throws Exception;

    public List<DetContratoAdquisicionRepBean> obtenerDetContratoAdquisicion(@Param("uniNeg") String uniNeg, @Param("idOrdenCompra") Integer idOrdenCompra) throws Exception;

}
