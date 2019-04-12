package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.BloqueoBean;
import pe.marista.sigma.dao.BloqueoDAO;

public class BloqueoService {

    private BloqueoDAO bloqueoDAO;

    public List<BloqueoBean> obtener(BloqueoBean bloqueoBean) throws Exception {
        return bloqueoDAO.obtener(bloqueoBean);
    }

    public BloqueoBean obtenerPorId(BloqueoBean bloqueoBean) throws Exception {
        return bloqueoDAO.obtenerPorId(bloqueoBean);
    }

    public List<BloqueoBean> obtenerBloqueoAnio(BloqueoBean bloqueoBean) throws Exception {
        return bloqueoDAO.obtenerBloqueoAnio(bloqueoBean);
    }

    @Transactional
    public void insertar(BloqueoBean bloqueoBean) throws Exception {
        bloqueoDAO.insertar(bloqueoBean);
    }

    @Transactional
    public void modificar(BloqueoBean bloqueoBean) throws Exception {
        bloqueoDAO.modificar(bloqueoBean);
    }

    @Transactional
    public void eliminar(BloqueoBean bloqueoBean) throws Exception {
        bloqueoDAO.eliminar(bloqueoBean);
    }

    public String obtenerIpServer(String uniNeg) throws Exception {
        return bloqueoDAO.obtenerIpServer(uniNeg);
    }

    public BloqueoDAO getBloqueoDAO() {
        return bloqueoDAO;
    }

    public void setBloqueoDAO(BloqueoDAO bloqueoDAO) {
        this.bloqueoDAO = bloqueoDAO;
    }

}
