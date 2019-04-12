package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.InventarioAlmacenBean;
import pe.marista.sigma.bean.MovimientoAlmacenBean;
import pe.marista.sigma.bean.reporte.InventarioAlmacenGeneralRepBean;
import pe.marista.sigma.bean.reporte.MovimientoAlmacenGeneralRepBean;
import pe.marista.sigma.bean.reporte.MovimientoAlmacenRepBean;

public interface InventarioAlmacenDAO {

    public List<InventarioAlmacenBean> obtenerTodosInventario() throws Exception;

    public InventarioAlmacenBean obtenerPorId(Integer idAlmacen) throws Exception;

    public InventarioAlmacenBean obtenerPorCatalogo(InventarioAlmacenBean inventarioAlmacenBean) throws Exception;

    public List<InventarioAlmacenBean> obtenerPorFiltro() throws Exception;

    public void insertar(InventarioAlmacenBean inventarioAlmacenBean) throws Exception;

    public List<InventarioAlmacenBean> obtenerTodos(String uniNeg) throws Exception;

    public List<InventarioAlmacenGeneralRepBean> obtenerTodosReporte(@Param("uniNeg") String uniNeg) throws Exception;
    
    public List<MovimientoAlmacenGeneralRepBean> obtenerTodosReporteMov(@Param("uniNeg") String uniNeg) throws Exception;

    public void actualizar(InventarioAlmacenBean inventarioAlmacenBean) throws Exception;

    public List<InventarioAlmacenBean> obtenerPorFiltroIAlmacen(InventarioAlmacenBean almacen) throws Exception;

    public void modificarStockActual(InventarioAlmacenBean inventarioAlmacenBean) throws Exception;

    public void modificarStockActualMov(InventarioAlmacenBean inventarioAlmacenBean) throws Exception;

    public void insertarMovimiento(MovimientoAlmacenBean movimientoAlmacenBean) throws Exception;

    public Integer obtenerUltimoMov(String uniNeg) throws Exception;

    public String obtenerUltimo(String uniNeg) throws Exception;

    public Integer obtenerUltimoPDF(String uniNeg) throws Exception;

    public List<MovimientoAlmacenBean> obtenerUltimoMovPDF(@Param("nroMovimiento") Integer nroMovimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public List<MovimientoAlmacenBean> obtenerTodosMovi(String uniNeg) throws Exception;

    public void eliminar(MovimientoAlmacenBean movimientoAlmacenBean) throws Exception;

    //CAMBIO A PROBAR
    public List<InventarioAlmacenBean> obtenerColorAlmacen(String uniNeg) throws Exception;

    public InventarioAlmacenBean obtenerColorAlmacenPorNro(@Param("uniNeg") String uniNeg, @Param("nroAlmacen") Integer nroAlmacen) throws Exception;

    public List<InventarioAlmacenBean> obtenerAlmacenPorNro(InventarioAlmacenBean inventarioAlmacenBean) throws Exception;

    public void eliminarInvAlmacen(Integer idAlmacen) throws Exception;

    public List<MovimientoAlmacenRepBean> obtenerReporteMovAlmacen(@Param("nroMovimiento") Integer nroMovimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public List<MovimientoAlmacenRepBean> obtenerUltimoMovPDF2(@Param("nroMovimiento") Integer nroMovimiento, @Param("uniNeg") String uniNeg) throws Exception;
    
}
