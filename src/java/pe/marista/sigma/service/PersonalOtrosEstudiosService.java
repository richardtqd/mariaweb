package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalOtrosEstudiosBean;
import pe.marista.sigma.bean.reporte.PersonalOtrosEstudiosRepBean;
import pe.marista.sigma.dao.PersonalOtrosEstudiosDAO;

public class PersonalOtrosEstudiosService {

    private PersonalOtrosEstudiosDAO personalOtrosEstudiosDAO;

    public List<PersonalOtrosEstudiosBean> obtenerPersonalOtrosEstudiosPorPersonal(PersonalBean personalBean) throws Exception {
        return personalOtrosEstudiosDAO.obtenerPersonalOtrosEstudiosPorPersonal(personalBean);
    }

    public List<PersonalOtrosEstudiosRepBean> obtenerOtrosEstudiosPorPersonalRep(Integer idPersonal, String uniNeg) throws Exception {
        return personalOtrosEstudiosDAO.obtenerOtrosEstudiosPorPersonalRep(idPersonal, uniNeg);
    }

    public PersonalOtrosEstudiosBean obtenerPersonalOtrosEstudiosPorId(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception {
        return personalOtrosEstudiosDAO.obtenerPersonalOtrosEstudiosPorId(personalOtrosEstudiosBean);
    }

    //Logica de Negocio
    @Transactional
    public void insertarPersonalOtrosEstudios(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception {
        personalOtrosEstudiosDAO.insertarPersonalOtrosEstudios(personalOtrosEstudiosBean);
    }

    @Transactional
    public void modificarPersonalOtrosEstudios(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception {
        personalOtrosEstudiosDAO.modificarPersonalOtrosEstudios(personalOtrosEstudiosBean);
    }

    @Transactional
    public void eliminarPersonalOtrosEstudios(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception {
        personalOtrosEstudiosDAO.eliminarPersonalOtrosEstudios(personalOtrosEstudiosBean);
    }

    // metodos getter y setter
    public PersonalOtrosEstudiosDAO getPersonalOtrosEstudiosDAO() {
        return personalOtrosEstudiosDAO;
    }

    public void setPersonalOtrosEstudiosDAO(PersonalOtrosEstudiosDAO personalOtrosEstudiosDAO) {
        this.personalOtrosEstudiosDAO = personalOtrosEstudiosDAO;
    }

}
