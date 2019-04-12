package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalIdiomaBean;
import pe.marista.sigma.bean.reporte.PersonalIdiomaRepBean;
import pe.marista.sigma.dao.PersonalIdiomaDAO;


public class PersonalIdiomaService {
    
    private PersonalIdiomaDAO personalIdiomaDAO;
    
    public List<PersonalIdiomaBean> obtenerPersonalIdiomaPorPersonal(PersonalBean personalBean) throws Exception{
        return  personalIdiomaDAO.obtenerPersonalIdiomaPorPersonal(personalBean);
    }
    public List<PersonalIdiomaRepBean> obtenerIdiomaPorPersonalRep(Integer idPersonal, String uniNeg) throws Exception{
        return  personalIdiomaDAO.obtenerIdiomaPorPersonalRep(idPersonal,uniNeg);
    }
    
    public PersonalIdiomaBean obtenerPersonalIdiomaPorId(PersonalIdiomaBean personalIdiomaBean) throws Exception{
        return personalIdiomaDAO.obtenerPersonalIdiomaPorId(personalIdiomaBean);
    }
    
    //Logica de Negocio
    @Transactional
    public void insertarPersonalIdioma(PersonalIdiomaBean personalIdiomaBean) throws Exception{
       personalIdiomaDAO.insertarPersonalIdioma(personalIdiomaBean);
    }
    @Transactional
    public void modificarPersonalIdioma(PersonalIdiomaBean personalIdiomaBean) throws Exception{
        personalIdiomaDAO.modificarPersonalIdioma(personalIdiomaBean);
    }
    
    @Transactional
    public void eliminarPersonalIdioma(PersonalIdiomaBean personalIdiomaBean) throws Exception {
        personalIdiomaDAO.eliminarPersonalIdioma(personalIdiomaBean);
    }
          
    
    // metodos getter y setter
    public PersonalIdiomaDAO getPersonalIdiomaDAO() {
        return personalIdiomaDAO;
    }

    public void setPersonalIdiomaDAO(PersonalIdiomaDAO personalIdiomaDAO) {
        this.personalIdiomaDAO = personalIdiomaDAO;
    }
    
    
}
