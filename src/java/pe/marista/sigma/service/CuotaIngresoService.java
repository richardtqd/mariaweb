package pe.marista.sigma.service;
 
import java.util.List;
import pe.marista.sigma.bean.CajaCuotaIngresoBean;
import pe.marista.sigma.bean.CuotaIngresoBean;
import pe.marista.sigma.bean.reporte.CuotaIngresoRepBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;
import pe.marista.sigma.dao.CuotaIngresoDAO;

public class CuotaIngresoService {
    private CuotaIngresoDAO cuotaIngresoDAO;

    public CuotaIngresoDAO getCuotaIngresoDAO() {
        return cuotaIngresoDAO;
    }

    public void setCuotaIngresoDAO(CuotaIngresoDAO cuotaIngresoDAO) {
        this.cuotaIngresoDAO = cuotaIngresoDAO;
    }

    public void insertarCajaCuotaIngreso(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception {
        cuotaIngresoDAO.insertarCajaCuotaIngreso(cajaCuotaIngresoBean);
    }

    public void modificarCierre(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception {
        cuotaIngresoDAO.modificarCierre(cajaCuotaIngresoBean);
    }

    public List<CajaCuotaIngresoBean> obtenerListaCuotaIngreso(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception {
        return cuotaIngresoDAO.obtenerListaCuotaIngreso(cajaCuotaIngresoBean);
    }

    public Integer obtenerMaxCaja(String uniNeg) throws Exception {
        return cuotaIngresoDAO.obtenerMaxCaja(uniNeg);
    }   

    public CajaCuotaIngresoBean obtenerCajaAbierta(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception {
        return cuotaIngresoDAO.obtenerCajaAbierta(cajaCuotaIngresoBean);
    }

    public void insertarCuotaIngreso(CuotaIngresoBean cuotaIngresoBean) throws Exception {
        cuotaIngresoDAO.insertarCuotaIngreso(cuotaIngresoBean);
    }

    public List<DocIngresoRepBean> obtenerCuotaIngreso(CuotaIngresoBean cuotaIngresoBean) throws Exception {
        return cuotaIngresoDAO.obtenerCuotaIngreso(cuotaIngresoBean);
    }

    public List<DetDocIngresoRepBean> obtenerFormatoDetalleCuotaIngreso(Integer idCuotaIngreso, String uniNeg) throws Exception {
        return cuotaIngresoDAO.obtenerFormatoDetalleCuotaIngreso(idCuotaIngreso, uniNeg);
    }

    public CuotaIngresoBean obtenerIdCuotaIngreso(CuotaIngresoBean cuotaIngresoBean) throws Exception {
        return cuotaIngresoDAO.obtenerIdCuotaIngreso(cuotaIngresoBean);
    }

    public void modificarMontoPorCaja(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception {
        cuotaIngresoDAO.modificarMontoPorCaja(cajaCuotaIngresoBean);
    } 

    public List<CuotaIngresoBean> obtenerIngresosEnCaja(CuotaIngresoBean cuotaIngresoBean) throws Exception {
        return cuotaIngresoDAO.obtenerIngresosEnCaja(cuotaIngresoBean);
    }

    public void cambioAnulado(CuotaIngresoBean CuotaIngresoBean) throws Exception {
        cuotaIngresoDAO.cambioAnulado(CuotaIngresoBean);
    }

    public List<CuotaIngresoRepBean> obtenerCuotaReporte(Integer idCajaCuotaIngreso, String uniNeg) throws Exception {
        return cuotaIngresoDAO.obtenerCuotaReporte(idCajaCuotaIngreso, uniNeg);
    }

    public List<CuotaIngresoBean> obtenerFiltroDetalleMovimientosCuoIng(CuotaIngresoBean cuotaIngresoBean) throws Exception {
        return cuotaIngresoDAO.obtenerFiltroDetalleMovimientosCuoIng(cuotaIngresoBean);
    }
    
    
}
