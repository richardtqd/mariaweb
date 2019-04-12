package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PaganteBean;
import pe.marista.sigma.dao.PaganteDAO;

public class PaganteService {

    private PaganteDAO paganteDAO;

    public List<PaganteBean> obtener(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtener(paganteBean);
    }

    public List<PaganteBean> filtrarPagante(PaganteBean paganteBean) throws Exception {
        return paganteDAO.filtrarPagante(paganteBean);
    }

    public List<PaganteBean> filtrarPaganteEst(PaganteBean paganteBean) throws Exception {
        return paganteDAO.filtrarPaganteEst(paganteBean);
    }

    public List<PaganteBean> filtrarPagantePer(PaganteBean paganteBean) throws Exception {
        return paganteDAO.filtrarPagantePer(paganteBean);
    }

    public List<PaganteBean> filtrarPaganteExt(PaganteBean paganteBean) throws Exception {
        return paganteDAO.filtrarPaganteExt(paganteBean);
    }

    public List<PaganteBean> filtrarPaganteEnt(PaganteBean paganteBean) throws Exception {
        return paganteDAO.filtrarPaganteEnt(paganteBean);
    }

    @Transactional
    public void insertar(PaganteBean paganteBean) throws Exception {
        paganteDAO.insertar(paganteBean);
    }

    @Transactional
    public void actualizar(PaganteBean paganteBean) throws Exception {
        paganteDAO.actualizar(paganteBean);
    }

    public PaganteBean obtenerPorId(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerPorId(paganteBean);
    }

    public PaganteBean obtenerPorIdPagEst(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerPorIdPagEst(paganteBean);
    }

    public PaganteBean obtenerPorIdPagPer(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerPorIdPagPer(paganteBean);
    }

    public PaganteBean obtenerPorIdPagExt(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerPorIdPagExt(paganteBean);
    }

    public PaganteBean obtenerPorIdPagEnt(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerPorIdPagEnt(paganteBean);
    }

    @Transactional
    public void eliminar(PaganteBean paganteBean) throws Exception {
        paganteDAO.eliminar(paganteBean);
    }

    @Transactional
    public Object execProPagante(String uniNeg, Integer estado, String creaPor) throws Exception {
        return paganteDAO.execProPagante(uniNeg, estado, creaPor);
    }

    public List<PaganteBean> filtrarPaganteObj(PaganteBean paganteBean) throws Exception {
        return paganteDAO.filtrarPaganteObj(paganteBean);
    }

    public List<PaganteBean> obtenerGrafoEstudiante(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerGrafoEstudiante(paganteBean);
    }

    public List<PaganteBean> obtenerGrafoFichaEstudiante(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerGrafoFichaEstudiante(paganteBean);
    }

    public List<PaganteBean> obtenerGrafoPersonal(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerGrafoPersonal(paganteBean);
    }

    public List<PaganteBean> obtenerGrafoExterno(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerGrafoExterno(paganteBean);
    }

    public List<PaganteBean> obtenerGrafoEntidad(PaganteBean paganteBean) throws Exception {
        return paganteDAO.obtenerGrafoEntidad(paganteBean);
    }

    public PaganteDAO getPaganteDAO() {
        return paganteDAO;
    }

    public void setPaganteDAO(PaganteDAO paganteDAO) {
        this.paganteDAO = paganteDAO;
    }

}
