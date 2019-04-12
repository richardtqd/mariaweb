/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.reporte.ConceptoPlanillaRepBean;
import pe.marista.sigma.bean.reporte.PlanillaCuentasCRRepBean;
import pe.marista.sigma.bean.reporte.PlanillaPersonalCRRepBean;
import pe.marista.sigma.dao.PersonalDAO;

/**
 *
 * @author Administrador
 */
public class PersonalService {

    private PersonalDAO personalDAO;

    //Metodos Getter y Setter
    public PersonalDAO getPersonalDAO() {
        return personalDAO;
    }

    public void setPersonalDAO(PersonalDAO personalDAO) {
        this.personalDAO = personalDAO;
    }

    //Lógica de Negocio
    public List<PersonalBean> obtenerTodos() throws Exception {
        return personalDAO.obtenerTodos();
    }

    public List<PersonalBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception {
        return personalDAO.obtenerPorUnidadNegocio(uniNeg);
    }

    public List<PersonalBean> obtenerPersonalCumpleanios(String uniNeg) throws Exception {
        return personalDAO.obtenerPersonalCumpleanios(uniNeg);
    }

    //Creacion dell metodo para movimientos de activos fijos
    public PersonalBean buscarPorId(Integer idPersonal) throws Exception {
        return personalDAO.buscarPorId(idPersonal);
    }

    public PersonalBean buscarPorIdPersonalLogistico(Integer idPersonal, String nombreUniOrg) throws Exception {
        return personalDAO.buscarPorIdPersonalLogistico(idPersonal, nombreUniOrg);
    }

    public List<PersonalBean> filtrarPersonal(PersonalBean personalBean) throws Exception {
        return personalDAO.filtrarPersonal(personalBean);
    }

    public PersonalBean obtenerCentrosPlanilla(Integer idPersonal, String uniNeg) throws Exception {
        return personalDAO.obtenerCentrosPlanilla(idPersonal, uniNeg);
    }

    public void modificarPlanillaCentros(PersonalBean personalBean) throws Exception {
        personalDAO.modificarPlanillaCentros(personalBean);
    }

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCR(String uniNeg, Integer status, Integer idTipoNivelIns) throws Exception {
        return personalDAO.obtenerPlanillaPersonalCR(uniNeg, status, idTipoNivelIns);
    }

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCRDetalle(String uniNeg, Integer status, Integer idTipoNivelIns) throws Exception {
        return personalDAO.obtenerPlanillaPersonalCRDetalle(uniNeg, status, idTipoNivelIns);
    }

    public List<ConceptoPlanillaRepBean> obtenerPlanillaRemuneracionesCabecera(String uniNeg, Integer mes, Integer anio) throws Exception {
        return personalDAO.obtenerPlanillaRemuneracionesCabecera(uniNeg, mes, anio);
    }

    public List<ConceptoPlanillaRepBean> obtenerPlanillaRemuneracionesDetalle(String uniNeg, Integer idNivelIns, Integer idObjeto, Integer mes, Integer anio) throws Exception {
        return personalDAO.obtenerPlanillaRemuneracionesDetalle(uniNeg, idNivelIns, idObjeto, mes, anio);
    }

    public List<ConceptoPlanillaRepBean> obtenerPlanillaRemuneracionesId(String uniNeg, Integer mes, Integer idNivelIns, Integer anio) throws Exception {
        return personalDAO.obtenerPlanillaRemuneracionesId(uniNeg, mes, idNivelIns, anio);
    }

    public List<ConceptoPlanillaRepBean> obtenerPlanillaCuenta(String uniNeg, Integer idNivelIns, Integer mes, Integer anio) throws Exception {
        return personalDAO.obtenerPlanillaCuenta(uniNeg, idNivelIns, mes, anio);
    }

    public List<ConceptoPlanillaRepBean> obtenerPlanillaCRCabecera(String uniNeg, Integer mes, Integer idNivelIns, Integer anio) throws Exception {
        return personalDAO.obtenerPlanillaCRCabecera(uniNeg, mes, idNivelIns, anio);
    }

    public List<ConceptoPlanillaRepBean> obtenerPlanillaCRDetalle(String uniNeg, Integer mes, Integer idNivelIns, Integer anio) throws Exception {
        return personalDAO.obtenerPlanillaCRDetalle(uniNeg, mes, idNivelIns, anio);
    }

    public List<ConceptoPlanillaRepBean> obtenerPlanillaCRSubDetalle(String uniNeg, Integer mes, Integer idNivelIns, Integer anio, Integer cr) throws Exception {
        return personalDAO.obtenerPlanillaCRSubDetalle(uniNeg, mes, idNivelIns, anio, cr);
    }

    public List<ConceptoPlanillaRepBean> obtenerTrabajadorCRCabecera(String uniNeg, Integer mes, Integer anio) throws Exception {
        return personalDAO.obtenerTrabajadorCRCabecera(uniNeg, mes, anio);
    }

    public List<ConceptoPlanillaRepBean> obtenerTrabajadorCRDetalle(String uniNeg, Integer mes, Integer anio, Integer idObjeto) throws Exception {
        return personalDAO.obtenerTrabajadorCRDetalle(uniNeg, mes, anio, idObjeto);
    }

    public List<ConceptoPlanillaRepBean> obtenerTrabajadorCRSubDetalle(String uniNeg, Integer mes, Integer anio, Integer cr, Integer idObjeto) throws Exception {
        return personalDAO.obtenerTrabajadorCRSubDetalle(uniNeg, mes, anio, cr, idObjeto);
    }

    public List<Integer> filtrarPersonalConCentros(String uniNeg) throws Exception {
        return personalDAO.filtrarPersonalConCentros(uniNeg);
    }

    public String validarDni(String nroDoc,String uniNeg) throws Exception {
        return personalDAO.validarDni(nroDoc,uniNeg);
    }

    public List<PersonalBean> duplicadosPersonal() throws Exception {
        return personalDAO.duplicadosPersonal();
    }     

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCRDetalleSinNada(String uniNeg, Integer status, Integer idTipoNivelIns) throws Exception {
        return personalDAO.obtenerPlanillaPersonalCRDetalleSinNada(uniNeg, status, idTipoNivelIns);
    } 

    public List<PlanillaPersonalCRRepBean> obtenerPlanillaPersonalCRTotales(String uniNeg, Integer status, Integer idTipoNivelIns) throws Exception {
        return personalDAO.obtenerPlanillaPersonalCRTotales(uniNeg, status, idTipoNivelIns);
    }

    public List<PersonalBean> obtenerPersonalUsuarios(PersonalBean personalBean) throws Exception {
        return personalDAO.obtenerPersonalUsuarios(personalBean);
    }

    public PersonalBean buscarPorCodPer(String idPersonal) throws Exception {
        return personalDAO.buscarPorCodPer(idPersonal);
    }

    public void modificarSueldoBasico(PersonalBean personalBean) throws Exception {
        personalDAO.modificarSueldoBasico(personalBean);
    }

    public void modificarSueldoBasicoEstatal(PersonalBean personalBean) throws Exception {
        personalDAO.modificarSueldoBasicoEstatal(personalBean);
    }
    
}
