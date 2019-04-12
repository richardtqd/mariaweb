package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalAlergiaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDescansoMedicoBean;
import pe.marista.sigma.bean.PersonalEvaPsicologicaBean;
import pe.marista.sigma.dao.PersonalAlergiaDAO;

public class PersonalAlergiaService {
    
    private PersonalAlergiaDAO personalAlergiaDAO;
    
    public List<PersonalAlergiaBean> obtenerPersonalAlergiaPorPersonal(PersonalBean personalBean)  throws Exception{
        return  personalAlergiaDAO.obtenerPersonalAlergiaPorPersonal(personalBean);
    }
    
    public PersonalAlergiaBean obtenerPersonalAlergiaPorId(PersonalAlergiaBean personalAlergiaBean) throws Exception{
        return personalAlergiaDAO.obtenerPersonalAlergiaPorId(personalAlergiaBean);
    }
    
    //Logica de Negocio
    @Transactional
    public void insertarPersonalAlergia(PersonalAlergiaBean personalAlergiaBean) throws Exception{
       personalAlergiaDAO.insertarPersonalAlergia(personalAlergiaBean);
    }
    @Transactional
    public void modificarPersonalAlergia(PersonalAlergiaBean personalAlergiaBean) throws Exception{
        personalAlergiaDAO.modificarPersonalAlergia(personalAlergiaBean);
    }
    
    @Transactional
    public void eliminarPersonalAlergia(PersonalAlergiaBean personalAlergiaBean) throws Exception {
        personalAlergiaDAO.eliminarPersonalAlergia(personalAlergiaBean);
    }
      
    // metodos getter y setter

    public PersonalAlergiaDAO getPersonalAlergiaDAO() {
        return personalAlergiaDAO;
    }

    public void setPersonalAlergiaDAO(PersonalAlergiaDAO personalAlergiaDAO) {
        this.personalAlergiaDAO = personalAlergiaDAO;
    }

    //Descanso Medico
    public List<PersonalDescansoMedicoBean> obtenerPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception {
        return personalAlergiaDAO.obtenerPersonalDescansoMedico(personalDescansoMedicoBean);
    }

    public List<PersonalDescansoMedicoBean> obtenerPersonalInasistencia(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception {
        return personalAlergiaDAO.obtenerPersonalInasistencia(personalDescansoMedicoBean);
    }

    public List<PersonalDescansoMedicoBean> obtenerPersonalAccidente(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception {
        return personalAlergiaDAO.obtenerPersonalAccidente(personalDescansoMedicoBean);
    }

    public PersonalDescansoMedicoBean obtenerPersonalDescansoMedicoPorId(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception {
        return personalAlergiaDAO.obtenerPersonalDescansoMedicoPorId(personalDescansoMedicoBean);
    }

    public void insertarPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception {
        personalAlergiaDAO.insertarPersonalDescansoMedico(personalDescansoMedicoBean);
    }

    public void modificarPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception {
        personalAlergiaDAO.modificarPersonalDescansoMedico(personalDescansoMedicoBean);
    }

    public void eliminarPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception {
        personalAlergiaDAO.eliminarPersonalDescansoMedico(personalDescansoMedicoBean);
    }

    //Evaluacion psicologica
    public List<PersonalEvaPsicologicaBean> obtenerPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception {
        return personalAlergiaDAO.obtenerPersonalEvaPsicologica(personalEvaPsicologicaBean);
    }

    public PersonalEvaPsicologicaBean obtenerPersonalEvaPsicologicaPorId(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception {
        return personalAlergiaDAO.obtenerPersonalEvaPsicologicaPorId(personalEvaPsicologicaBean);
    }

    public void insertarPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception {
        personalAlergiaDAO.insertarPersonalEvaPsicologica(personalEvaPsicologicaBean);
    }

    public void modificarPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception {
        personalAlergiaDAO.modificarPersonalEvaPsicologica(personalEvaPsicologicaBean);
    }

    public void eliminarPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception {
        personalAlergiaDAO.eliminarPersonalEvaPsicologica(personalEvaPsicologicaBean);
    }
    
    
}
