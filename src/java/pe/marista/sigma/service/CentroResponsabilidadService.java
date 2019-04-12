/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.dao.CentroResponsabilidadDAO;

/**
 *
 * @author Administrador
 */
public class CentroResponsabilidadService {

    private CentroResponsabilidadDAO centroResponsabilidadDAO;

    public List<CentroResponsabilidadBean> obtenerCentroResponsabilidad() throws Exception {
        return centroResponsabilidadDAO.obtenerCentroResponsabilidad();
    }

    public List<CentroResponsabilidadBean> obtenerCentroResNivel3() throws Exception {
        return centroResponsabilidadDAO.obtenerCentroResNivel3();
    }

    public CentroResponsabilidadBean obtenerCentroResPorNombre(String nombre) throws Exception {
        return centroResponsabilidadDAO.obtenerCentroResPorNombre(nombre);
    }

    public List<CentroResponsabilidadBean> obtenerIdTipoCR(Integer idTipoGrupoCR) throws Exception {
        return centroResponsabilidadDAO.obtenerIdTipoCR(idTipoGrupoCR);
    }

    public void insertarCentroResponsabilidad(CentroResponsabilidadBean centroResponsabilidadBean) throws Exception {
        centroResponsabilidadDAO.insertarCentroResponsabilidad(centroResponsabilidadBean);
    }

    public void modificarCentroResponsabilidad(CentroResponsabilidadBean centroResponsabilidadBean) throws Exception {
        centroResponsabilidadDAO.modificarCentroResponsabilidad(centroResponsabilidadBean);
    }

    public CentroResponsabilidadBean obtenerCRPorId(Integer cr) throws Exception {
        return centroResponsabilidadDAO.obtenerCRPorId(cr);
    }

    public Integer eliminarCentroResposabilidad(Integer cr) throws Exception {
        return centroResponsabilidadDAO.eliminarCentroResposabilidad(cr);
    }

    public CentroResponsabilidadDAO getCentroResponsabilidadDAO() {
        return centroResponsabilidadDAO;
    }

    public void setCentroResponsabilidadDAO(CentroResponsabilidadDAO centroResponsabilidadDAO) {
        this.centroResponsabilidadDAO = centroResponsabilidadDAO;
    }

    public List<CentroResponsabilidadBean> obtenerCentroInicial() throws Exception {
        return centroResponsabilidadDAO.obtenerCentroInicial();
    }

    public List<CentroResponsabilidadBean> obtenerCentroPrimaria() throws Exception {
        return centroResponsabilidadDAO.obtenerCentroPrimaria();
    }

    public List<CentroResponsabilidadBean> obtenerCentroSecundaria() throws Exception {
        return centroResponsabilidadDAO.obtenerCentroSecundaria();
    }

    public List<CentroResponsabilidadBean> obtenerCentroBachiller() throws Exception {
        return centroResponsabilidadDAO.obtenerCentroBachiller();
    }

    public ViewMatriculaBean obtenerTotales(ViewMatriculaBean viewMatriculaBean) throws Exception {
        return centroResponsabilidadDAO.obtenerTotales(viewMatriculaBean);
    }

    //Cambio Act_CR  
    public List<CentroResponsabilidadBean> obtenerCrInAct(Integer idActividad) throws Exception {
        return centroResponsabilidadDAO.obtenerCrInAct(idActividad);
    }

    public List<CentroResponsabilidadBean> obtenerCrOutAct(Integer idActividad) throws Exception {
        return centroResponsabilidadDAO.obtenerCrOutAct(idActividad);
    }

    public List<CentroResponsabilidadBean> obtenerPresCr(String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerPresCr(uniNeg);
    }

    //CR Requerimiento
    public List<CentroResponsabilidadBean> obtenerInCrReque(Integer idRequerimiento, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerInCrReque(idRequerimiento, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerOutCrReque(Integer idRequerimiento, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerOutCrReque(idRequerimiento, uniNeg);
    }

    //CR Requerimiento
    public List<CentroResponsabilidadBean> obtenerInCrRegistro(Integer idRegistroCompra, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerInCrReque(idRegistroCompra, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerOutCrRegistro(Integer idRegistroCompra, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerOutCrReque(idRegistroCompra, uniNeg);
    }

    //CR caja ch
    public List<CentroResponsabilidadBean> obtenerInCrSolCaj(Integer idSolicitudCajaCh, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerInCrSolCaj(idSolicitudCajaCh, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerOutCrSolCaj(Integer idSolicitudCajaCh, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerOutCrSolCaj(idSolicitudCajaCh, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerOutCrLiq(Integer idSolicitudCajaCh, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerOutCrLiq(idSolicitudCajaCh, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerPorUniOrg(Integer idUniOrg) throws Exception {
        return centroResponsabilidadDAO.obtenerPorUniOrg(idUniOrg);
    }

    public List<CentroResponsabilidadBean> obtenerPorUniOrgPadre(Integer idUniOrg) throws Exception {
        return centroResponsabilidadDAO.obtenerPorUniOrgPadre(idUniOrg);
    }

    public List<CentroResponsabilidadBean> obtenerInCrAcceso(Integer idTipoAcceso, Integer anio, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerInCrAcceso(idTipoAcceso, anio, uniNeg);
    }

    public List<CentroResponsabilidadBean> obtenerOutCrAcceso(Integer idTipoAcceso, Integer anio, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerOutCrAcceso(idTipoAcceso, anio, uniNeg);
    }

    
    // filtro
    public List<CentroResponsabilidadBean> obtenerCrPorNivelAcademico(String nomNivAca) throws Exception {
        return centroResponsabilidadDAO.obtenerCrPorNivelAcademico(nomNivAca);
    }

    //CR POR DETACTIVIDAD 
    public List<CentroResponsabilidadBean> obtenerCrInDetAct(DetActividadBean detActividadBean) throws Exception {
        return centroResponsabilidadDAO.obtenerCrInDetAct(detActividadBean);
    }

    public List<CentroResponsabilidadBean> obtenerCrOutDetAct(DetActividadBean detActividadBean) throws Exception {
        return centroResponsabilidadDAO.obtenerCrOutDetAct(detActividadBean);
    }

    public List<CentroResponsabilidadBean> obtenerCentroResponsabilidadVista() throws Exception {
        return centroResponsabilidadDAO.obtenerCentroResponsabilidadVista();
    }

    public List<CentroResponsabilidadBean> obtenerInCrAccesoPorNivel(List<Integer> ids, Integer anio, String uniNeg) throws Exception {
        return centroResponsabilidadDAO.obtenerInCrAccesoPorNivel(ids, anio, uniNeg);
    }
    
    
 
}
