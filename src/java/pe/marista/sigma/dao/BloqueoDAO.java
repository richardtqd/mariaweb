package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.BloqueoBean;

public interface BloqueoDAO {

    public List<BloqueoBean> obtener(BloqueoBean bloqueoBean) throws Exception;

    public BloqueoBean obtenerPorId(BloqueoBean bloqueoBean) throws Exception;

    public List<BloqueoBean> obtenerBloqueoAnio(BloqueoBean bloqueoBean) throws Exception;

    public void insertar(BloqueoBean bloqueoBean) throws Exception;

    public void modificar(BloqueoBean bloqueoBean) throws Exception;

    public void eliminar(BloqueoBean bloqueoBean) throws Exception;

    public String obtenerIpServer(String uniNeg) throws Exception;

}
