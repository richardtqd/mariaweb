package pe.marista.sigma.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalFormacionBean;
import pe.marista.sigma.bean.PersonalFormacionCarismaBean;
import pe.marista.sigma.bean.reporte.PersonalFormacionRepBean;
import pe.marista.sigma.dao.PersonalFormacionDAO;

public class PersonalFormacionService {

    private PersonalFormacionDAO personalFormacionDAO;

    public List<PersonalFormacionBean> obtenerPersonalFormacionSuperiorPorPersonal(PersonalBean personalBean) throws Exception {
        return personalFormacionDAO.obtenerPersonalFormacionSuperiorPorPersonal(personalBean);
    }

    public List<PersonalFormacionBean> obtenerPersonalFormacionBasicaPorPersonal(PersonalBean personalBean) throws Exception {
        return personalFormacionDAO.obtenerPersonalFormacionBasicaPorPersonal(personalBean);
    }

    public PersonalFormacionBean obtenerPersonalFormacionSuperiorPorId(PersonalFormacionBean personalFormacionBean) throws Exception {
        return personalFormacionDAO.obtenerPersonalFormacionSuperiorPorId(personalFormacionBean);
    }

    public PersonalFormacionBean obtenerPersonalFormacionBasicaPorId(PersonalFormacionBean personalFormacionBean) throws Exception {
        return personalFormacionDAO.obtenerPersonalFormacionBasicaPorId(personalFormacionBean);
    }

    public List<PersonalFormacionRepBean> obtenerFormacionBasicaPorPersonalRep(Integer idPersonal, String uniNeg) throws Exception {
        return personalFormacionDAO.obtenerFormacionBasicaPorPersonalRep(idPersonal, uniNeg);
    }

    public List<PersonalFormacionRepBean> obtenerFormacionSuperiorPorPersonalRep(Integer idPersonal, String uniNeg) throws Exception {
        return personalFormacionDAO.obtenerFormacionSuperiorPorPersonalRep(idPersonal, uniNeg);
    }

    //Logica de Negocio
    @Transactional
    public void insertarPersonalFormacionSuperior(PersonalFormacionBean personalFormacionBean) throws Exception {
        personalFormacionDAO.insertarPersonalFormacionSuperior(personalFormacionBean);
    }

    @Transactional
    public void insertarPersonalFormacionBasica(PersonalFormacionBean personalFormacionBean) throws Exception {
        personalFormacionDAO.insertarPersonalFormacionBasica(personalFormacionBean);
    }

    @Transactional
    public void modificarPersonalFormacionSuperior(PersonalFormacionBean personalFormacionBean) throws Exception {
        personalFormacionDAO.modificarPersonalFormacionSuperior(personalFormacionBean);
    }

    @Transactional
    public void modificarPersonalFormacionBasica(PersonalFormacionBean personalFormacionBean) throws Exception {
        personalFormacionDAO.modificarPersonalFormacionBasica(personalFormacionBean);
    }

    @Transactional
    public void eliminarPersonalFormacionSuperior(PersonalFormacionBean personalFormacionBean) throws Exception {
        personalFormacionDAO.eliminarPersonalFormacionSuperior(personalFormacionBean);
    }

    @Transactional
    public void eliminarPersonalFormacionBasica(PersonalFormacionBean personalFormacionBean) throws Exception {
        personalFormacionDAO.eliminarPersonalFormacionBasica(personalFormacionBean);
    }

    // metodos getter y setter
    public PersonalFormacionDAO getPersonalFormacionDAO() {
        return personalFormacionDAO;
    }

    public void setPersonalFormacionDAO(PersonalFormacionDAO personalFormacionDAO) {
        this.personalFormacionDAO = personalFormacionDAO;
    }

    public List<PersonalFormacionBean> obtenerPersonalFormacionBasicaPorPersonalNew(PersonalBean personalBean) throws Exception {
        return personalFormacionDAO.obtenerPersonalFormacionBasicaPorPersonalNew(personalBean);
    }

    public void insertarPersonalFormacionCarisma(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception {
        personalFormacionDAO.insertarPersonalFormacionCarisma(personalFormacionCarismaBean);
    }

    public void modificarPersonalFormacionCarisma(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception {
        personalFormacionDAO.modificarPersonalFormacionCarisma(personalFormacionCarismaBean);
    }

    public void eliminarPersonalFormacionCarisma(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception {
        personalFormacionDAO.eliminarPersonalFormacionCarisma(personalFormacionCarismaBean);
    }

    public PersonalFormacionCarismaBean obtenerPersonalFormacionCarismaID(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception {
        return personalFormacionDAO.obtenerPersonalFormacionCarismaID(personalFormacionCarismaBean);
    }

    public List<PersonalFormacionCarismaBean> obtenerPersonalFormacionCarismaLista(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception {
        return personalFormacionDAO.obtenerPersonalFormacionCarismaLista(personalFormacionCarismaBean);
    }

}
