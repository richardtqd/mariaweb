package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.dao.MotivoMovimientoDAO;
import pe.marista.sigma.bean.MotivoMovimientoBean;


public class MotivoMovimientoService {
     private MotivoMovimientoDAO motivoMovimientoDAO;
     
     public MotivoMovimientoBean obtenerMotivoPorId(Integer idMovimientoMotivo) throws Exception {
        return motivoMovimientoDAO.obtenerMotivoPorId(idMovimientoMotivo);
    }
     public List<MotivoMovimientoBean> obtenerMotivo(Integer idMovimientoMotivo) throws Exception{
       return motivoMovimientoDAO.obtenerMotivo(idMovimientoMotivo);
    }

    public MotivoMovimientoDAO getMotivoMovimientoDAO() {
        return motivoMovimientoDAO;
    }

    public void setMotivoMovimientoDAO(MotivoMovimientoDAO motivoMovimientoDAO) {
        this.motivoMovimientoDAO = motivoMovimientoDAO;
    }

    public MotivoMovimientoBean obtenerId(MotivoMovimientoBean motivoMovimientoBean) throws Exception {
        return motivoMovimientoDAO.obtenerId(motivoMovimientoBean);
    }

    public List<MotivoMovimientoBean> obtenerTemporal(Integer idTipoDuracion) throws Exception {
        return motivoMovimientoDAO.obtenerTemporal(idTipoDuracion);
    }

    public List<MotivoMovimientoBean> obtenerPermanente(Integer idTipoDuracion) throws Exception {
        return motivoMovimientoDAO.obtenerPermanente(idTipoDuracion);
    }

     
    
}
