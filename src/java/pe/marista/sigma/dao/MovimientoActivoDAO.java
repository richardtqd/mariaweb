package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.MotivoMovimientoBean;
import pe.marista.sigma.bean.MovimientoActivoBean;
import pe.marista.sigma.bean.reporte.MovimientoActivoGeneralRepBean;

public interface MovimientoActivoDAO {

    public List<MovimientoActivoBean> ObtenerTodos() throws Exception;

    public MovimientoActivoBean ObtenerPorId(Integer idRequerimiento) throws Exception;
    
    public MotivoMovimientoBean ObtenerPorIdMtivoMov(MotivoMovimientoBean motivoMovimientoBean) throws Exception;
    
    public MovimientoActivoBean ObtenerPorIdMovimiento(MovimientoActivoBean movimientoActivoBean) throws Exception;

    public List<MovimientoActivoBean> ObtenerPorFiltro(MovimientoActivoBean movimientoActivoBean) throws Exception;

    public void insertar(MovimientoActivoBean movimientoActivoBean) throws Exception;

    public void Actualizar(MovimientoActivoBean movimientoActivoBean) throws Exception;

    public List<CatalogoBean> ObtenerIAPorItem(Integer idcatalogo) throws Exception;

    public List<CatalogoBean> ObtenerPorItem(CatalogoBean catalogoBean) throws Exception;

    public MovimientoActivoBean obtenerInventarioPorId(Integer idMovimientoActivo) throws Exception;

    public List<MovimientoActivoBean> obtenerPorMovimiento(Integer idMovimientoActivo) throws Exception;

    public List<MovimientoActivoBean> obtenerPorFiltroMovimientos(MovimientoActivoBean movimiento) throws Exception;
    
    public Integer obtenerUltimoMovimiento(MovimientoActivoBean movimientoActivoBean) throws Exception;
    
    public List<MovimientoActivoBean> obtenerPorMovimientoPDF(MovimientoActivoBean movimientoActivoBean) throws Exception;
    
    public Integer obtenerUltimoDocMovAct(MovimientoActivoBean movimientoActivoBean) throws Exception;
    
    public List<MovimientoActivoBean> obtenerMovimiento(MovimientoActivoBean movimientoActivoBean) throws Exception;
    
    public List<MotivoMovimientoBean> obtenerMotivoPorMov(Integer idTipoMov) throws Exception;
    
    public List<CodigoBean> obtenerMotivoPorDuracion(Integer idTipoDuracion) throws Exception;
    
    public List<MovimientoActivoGeneralRepBean> obtenerMovimientoGeneral(@Param("uniNeg") String uniNeg) throws Exception;
}
