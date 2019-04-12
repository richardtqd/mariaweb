package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;

public interface OrdenCompraDAO {

    public List<OrdenCompraBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception;

    public OrdenCompraBean obtenerPorId(OrdenCompraBean ordenCompraBean) throws Exception;

    public List<OrdenCompraBean> obtenerListaPorId(Integer idordenCompra) throws Exception;

    public List<OrdenCompraBean> obtenerPorFiltro(OrdenCompraBean orden) throws Exception;

    public void insertar(OrdenCompraBean ordenCompraBean) throws Exception;

    public void insertarCotizacionOrden(OrdenCompraBean ordenCompraBean) throws Exception;

    public void actualizar(OrdenCompraBean ordenCompraBean) throws Exception;

    public Integer obtenerUltimo(String uniNeg) throws Exception;

    public List<OrdenCompraBean> obtenerTodosSolDes(OrdenCompraBean bean) throws Exception;

    public List<OrdenCompraBean> obtenerTodosM(OrdenCompraBean bean) throws Exception;

    public SolicitudLogisticoBean obtenerPorSol(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    //cambia estado para RGC
    public void cambiarEstadoOrdenCompraRegistrado(OrdenCompraBean ordenCompraBean) throws Exception;

    public String obtenerUltimoOrden(String uniNeg) throws Exception;

}
