package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ActividadBean;
import pe.marista.sigma.dao.ActividadDAO;

public class ActividadService {

    private ActividadDAO actividadDAO;

    //Getter and Setter
    public ActividadDAO getActividadDAO() {
        return actividadDAO;
    }

    public void setActividadDAO(ActividadDAO actividadDAO) {
        this.actividadDAO = actividadDAO;
    }

    //Metodos Lógica de Negocio
    public List<ActividadBean> obtenerTodos(String uniNeg) throws Exception {
        return actividadDAO.obtenerTodos(uniNeg);
    }

    public List<ActividadBean> obtenerPorPlanOperativo(Integer idUniOrg, String uniNeg) throws Exception {
        return actividadDAO.obtenerPorPlanOperativo(idUniOrg, uniNeg);
    }

    public String obtenerActExec(Integer idActividad, String uniNeg) throws Exception {
        return actividadDAO.obtenerActExec(idActividad, uniNeg);
    }

    public void actualizarActividad(ActividadBean actividadBean) throws Exception {
        actividadDAO.actualizarActividad(actividadBean);
    }

    public void eliminarActividad(Integer idActividad) throws Exception {
        actividadDAO.eliminarActividad(idActividad);
    }

    public List<ActividadBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception {
        return actividadDAO.obtenerPorUnidadNegocio(uniNeg);
    }

    public Integer obtenerMaxId(String uniNeg) throws Exception {
        return actividadDAO.obtenerMaxId(uniNeg);
    }

    public List<ActividadBean> obtenerListaPorId(Integer idActividad, String uniNeg) throws Exception {
        return actividadDAO.obtenerListaPorId(idActividad, uniNeg);
    }

    public ActividadBean obtenerPorId(Integer idActividad) throws Exception {
        return actividadDAO.obtenerPorId(idActividad);
    }

    public List<ActividadBean> obtenerPorObjOpe(Integer idobjoperativo) throws Exception {
        return actividadDAO.obtenerPorObjOpe(idobjoperativo);
    }

    public List<ActividadBean> obtenerPorUnidadOrganica(Integer idUniOrg, String uniNeg, Integer anio) throws Exception {
        return actividadDAO.obtenerPorUnidadOrganica(idUniOrg, uniNeg, anio);
    }

    @Transactional
    public void insertarActividad(ActividadBean actividadBean) throws Exception {
        actividadDAO.insertarActividad(actividadBean);
    }

    public List<ActividadBean> obtenerPorUnidadNegocioUniOrg(Integer idUniOrg, String uniNeg) throws Exception {
        return actividadDAO.obtenerPorUnidadNegocioUniOrg(idUniOrg, uniNeg);
    }

    public List<ActividadBean> obtenerObjPorAnio(ActividadBean actividadBean) throws Exception {
        return actividadDAO.obtenerObjPorAnio(actividadBean);
    }

    public ActividadBean obtenerObjPorId(ActividadBean actividadBean) throws Exception {
        return actividadDAO.obtenerObjPorId(actividadBean);
    }

    public BigDecimal obtenerImporteActividad(ActividadBean actividadBean) throws Exception {
        return actividadDAO.obtenerImporteActividad(actividadBean);
    }

}
