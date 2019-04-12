package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.dao.TipoSolicitudDAO;

/**
 *
 * @author Administrador
 */
public class TipoSolicitudService {

    private TipoSolicitudDAO tipoSolicitudDAO;

    //Metodos Lógica de Negocio
    @Transactional
    public void insertarTipoSolicitud(TipoSolicitudBean tipoCodigoBean) throws Exception {
        tipoSolicitudDAO.insertarTipoSolicitud(tipoCodigoBean);
    }

    @Transactional
    public void modificarTipoSolicitud(TipoSolicitudBean tipoCodigoBean) throws Exception {
        tipoSolicitudDAO.modificarTipoSolicitud(tipoCodigoBean);
    }

    @Transactional
    public void eliminarTipoSolicitud(TipoSolicitudBean tipoCodigoBean) throws Exception {
        tipoSolicitudDAO.eliminarTipoSolicitud(tipoCodigoBean);
    }

    public List<TipoSolicitudBean> obtenerTodosTipoSolicitudPorUniNeg(String uniNeg) throws Exception {
        return tipoSolicitudDAO.obtenerTodosTipoSolicitudPorUniNeg(uniNeg);
    }

    public List<TipoSolicitudBean> obtenerPorAmbitoPorUniNeg(TipoSolicitudBean tipoSolicitudBean) throws Exception {
        return tipoSolicitudDAO.obtenerPorAmbitoPorUniNeg(tipoSolicitudBean);
    }

    public List<TipoSolicitudBean> obtenerSolGenCajaCH(String general, String cajach, String uniNeg) throws Exception {
        return tipoSolicitudDAO.obtenerSolGenCajaCH(general, cajach, uniNeg);
    }

    public List<TipoSolicitudBean> obtenerTipoSolicitudPorFiltro(TipoSolicitudBean tipoSolicitudBean) throws Exception {
        return tipoSolicitudDAO.obtenerTipoSolicitudPorFiltro(tipoSolicitudBean);
    }

    public TipoSolicitudBean obtenerTipoSolicitudPorId(TipoSolicitudBean tipoCodigoBean) throws Exception {
        return tipoSolicitudDAO.obtenerTipoSolicitudPorId(tipoCodigoBean);
    }

    public TipoSolicitudBean obtenerTipoSolicitudPorNombre(TipoSolicitudBean tipoCodigoBean) throws Exception {
        return tipoSolicitudDAO.obtenerTipoSolicitudPorNombre(tipoCodigoBean);
    }

    public TipoSolicitudBean validarTipSolConAuto(TipoSolicitudBean tipoCodigoBean) throws Exception {
        return tipoSolicitudDAO.validarTipSolConAuto(tipoCodigoBean);
    }

    public String obtenerCorreoCorPorAutorizador(String nombre, String uniNeg) throws Exception { 
        return tipoSolicitudDAO.obtenerCorreoCorPorAutorizador(nombre, uniNeg);
    }

    //GEtter and SEtter
    public TipoSolicitudDAO getTipoSolicitudDAO() {
        return tipoSolicitudDAO;
    }

    public void setTipoSolicitudDAO(TipoSolicitudDAO tipoSolicitudDAO) {
        this.tipoSolicitudDAO = tipoSolicitudDAO;
    }

}
