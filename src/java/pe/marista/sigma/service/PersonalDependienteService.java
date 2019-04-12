package pe.marista.sigma.service;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDependienteBean;
import pe.marista.sigma.bean.reporte.PersonalDependienteRepBean;
import pe.marista.sigma.dao.PersonalDependienteDAO;

/**
 *
 * @author MS-005
 */
public class PersonalDependienteService {

    private PersonalDependienteDAO personalDependienteDAO;

    public List<PersonalDependienteBean> obtenerPersonalDependientePorPersonal(PersonalBean personalBean) throws Exception {
        return personalDependienteDAO.obtenerPersonalDependientePorPersonal(personalBean);
    }

    public PersonalDependienteBean obtenerPersonalDependientePorId(PersonalDependienteBean personalDependienteBean) throws Exception {
        return personalDependienteDAO.obtenerPersonalDependientePorId(personalDependienteBean);
    }

    //Logica de Negocio
    @Transactional
    public void insertarPersonalDependiente(PersonalDependienteBean personalDependienteBean) throws Exception {
        personalDependienteDAO.insertarPersonalDependiente(personalDependienteBean);
    }

    @Transactional
    public void modificarPersonalDependiente(PersonalDependienteBean personalDependienteBean) throws Exception {
        personalDependienteDAO.modificarPersonalDependiente(personalDependienteBean);
    }

    @Transactional
    public void eliminarPersonalDependiente(PersonalDependienteBean personalDependienteBean) throws Exception {
        personalDependienteDAO.eliminarPersonalDependiente(personalDependienteBean);
    }

    @Transactional
    public void cambiarEstadoDependiente(PersonalDependienteBean personalDependienteBean) throws Exception {
        personalDependienteDAO.cambiarEstadoDependiente(personalDependienteBean);
    }

    public List<PersonalDependienteRepBean> obtenerDependientePorPersonalRep(Integer idPersonal, String uniNeg) throws Exception {
        return personalDependienteDAO.obtenerDependientePorPersonalRep(idPersonal, uniNeg);
    }

    // metodos getter y setter
    public PersonalDependienteDAO getPersonalDependienteDAO() {
        return personalDependienteDAO;
    }

    public void setPersonalDependienteDAO(PersonalDependienteDAO personalDependienteDAO) {
        this.personalDependienteDAO = personalDependienteDAO;
    }

    public String obtenerDependienteFechaNacimiento(Date fechaNacimiento) throws Exception {
        return personalDependienteDAO.obtenerDependienteFechaNacimiento(fechaNacimiento);
    }

    
}
