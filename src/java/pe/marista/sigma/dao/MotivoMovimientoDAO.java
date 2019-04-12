package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.MotivoMovimientoBean;
import pe.marista.sigma.bean.MovimientoActivoBean;

public interface MotivoMovimientoDAO {

    public MotivoMovimientoBean obtenerMotivoPorId(Integer idMovimientoMotivo) throws Exception;

    public List<MotivoMovimientoBean> obtenerMotivo(Integer idMovimientoMotivo) throws Exception;

    public MotivoMovimientoBean obtenerId(MotivoMovimientoBean motivoMovimientoBean) throws Exception;

    public List<MotivoMovimientoBean> obtenerTemporal(Integer idTipoDuracion) throws Exception;

    public List<MotivoMovimientoBean> obtenerPermanente(Integer idTipoDuracion) throws Exception;
}
