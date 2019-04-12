package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.MovimientoActivoBean;
import pe.marista.sigma.dao.MovimientoActivoDAO;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.MotivoMovimientoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.MovimientoActivoGeneralRepBean;

public class MovimientoActivoService {

    private MovimientoActivoDAO movimientoActivoDAO;

    public List<MovimientoActivoBean> ObtenerTodos() throws Exception {
        return movimientoActivoDAO.ObtenerTodos();
    }

    public MovimientoActivoBean ObtenerPorId(Integer idRequerimiento) throws Exception {
        return movimientoActivoDAO.ObtenerPorId(idRequerimiento);
    }

    public List<MovimientoActivoBean> ObtenerPorFiltro(MovimientoActivoBean movimientoActivoBean) throws Exception {
        return movimientoActivoDAO.ObtenerPorFiltro(movimientoActivoBean);
    }

    public void insertar(MovimientoActivoBean movimientoActivoBean) throws Exception { 
            movimientoActivoDAO.insertar(movimientoActivoBean); 
    }

    public void Actualizar(MovimientoActivoBean movimientoActivoBean) throws Exception {
        movimientoActivoDAO.Actualizar(movimientoActivoBean);
    }

    public MovimientoActivoDAO getMovimientoActivoDAO() {
        return movimientoActivoDAO;
    }

    public MovimientoActivoBean obtenerInventarioPorId(Integer idInventarioActivo) throws Exception {
        return movimientoActivoDAO.obtenerInventarioPorId(idInventarioActivo);
    }

    public MovimientoActivoBean ObtenerPorIdMovimiento(MovimientoActivoBean movimientoActivoBean) throws Exception {
        return movimientoActivoDAO.ObtenerPorIdMovimiento(movimientoActivoBean);
    }

    public MotivoMovimientoBean ObtenerPorIdMtivoMov(MotivoMovimientoBean motivoMovimientoBean) throws Exception {
        return movimientoActivoDAO.ObtenerPorIdMtivoMov(motivoMovimientoBean);
    }

    public void setMovimientoActivoDAO(MovimientoActivoDAO movimientoActivoDAO) {
        this.movimientoActivoDAO = movimientoActivoDAO;
    }

    public List<CatalogoBean> ObtenerIAPorItem(Integer idcatalogo) throws Exception {
        return movimientoActivoDAO.ObtenerIAPorItem(idcatalogo);
    }

    public List<CatalogoBean> ObtenerPorItem(CatalogoBean catalogoBean) throws Exception {
        return movimientoActivoDAO.ObtenerPorItem(catalogoBean);
    }

    public List<MovimientoActivoBean> obtenerPorMovimiento(Integer idMovimientoActivo) throws Exception {
        return movimientoActivoDAO.obtenerPorMovimiento(idMovimientoActivo);
    }

    public List<MovimientoActivoBean> obtenerPorFiltroMovimientos(MovimientoActivoBean movimiento) throws Exception {
        return movimientoActivoDAO.obtenerPorFiltroMovimientos(movimiento);
    }

    public Integer obtenerUltimoMovimiento(MovimientoActivoBean movimientoActivoBean) throws Exception {
        return movimientoActivoDAO.obtenerUltimoMovimiento(movimientoActivoBean);
    }

    public List<MovimientoActivoBean> obtenerPorMovimientoPDF(MovimientoActivoBean movimientoActivoBean) throws Exception {
        return movimientoActivoDAO.obtenerPorMovimientoPDF(movimientoActivoBean);
    }

    public List<MovimientoActivoBean> obtenerMovimiento(MovimientoActivoBean movimientoActivoBean) throws Exception {
        return movimientoActivoDAO.obtenerMovimiento(movimientoActivoBean);
    }

     public List<MotivoMovimientoBean> obtenerMotivoPorMov(Integer idTipoMov) throws Exception{
       return movimientoActivoDAO.obtenerMotivoPorMov(idTipoMov);
    }

    public List<MovimientoActivoGeneralRepBean> obtenerMovimientoGeneral(String uniNeg) throws Exception {
        return movimientoActivoDAO.obtenerMovimientoGeneral(uniNeg);
    } 
}
