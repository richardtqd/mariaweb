package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalEnfermedadBean;
import pe.marista.sigma.dao.PersonalEnfermedadDAO;

public class PersonalEnfermedadService {
    
    private PersonalEnfermedadDAO personalEnfermedadDAO;
    
    public List<PersonalEnfermedadBean> obtenerPersonalEnfermedadPorPersonal(PersonalBean personalBean) throws Exception{
        return  personalEnfermedadDAO.obtenerPersonalEnfermedadPorPersonal(personalBean);
    }
    
    public PersonalEnfermedadBean obtenerPersonalEnfermedadPorId(PersonalEnfermedadBean personalEnfermedadBean) throws Exception{
        return personalEnfermedadDAO.obtenerPersonalEnfermedadPorId(personalEnfermedadBean);
    }
    
    //Logica de Negocio
    @Transactional
    public void insertarPersonalEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception{
       personalEnfermedadDAO.insertarPersonalEnfermedad(personalEnfermedadBean);
    }
    @Transactional
    public void modificarPersonalEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception{
        personalEnfermedadDAO.modificarPersonalEnfermedad(personalEnfermedadBean);
    }
    
    @Transactional
    public void eliminarPersonalEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception {
        personalEnfermedadDAO.eliminarPersonalEnfermedad(personalEnfermedadBean);
    }
    
    @Transactional
    public void cambiarEstadoEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception{
        personalEnfermedadDAO.cambiarEstadoEnfermedad(personalEnfermedadBean);
    }
        
    
    // metodos getter y setter

    public PersonalEnfermedadDAO getPersonalEnfermedadDAO() {
        return personalEnfermedadDAO;
    }

    public void setPersonalEnfermedadDAO(PersonalEnfermedadDAO personalEnfermedadDAO) {
        this.personalEnfermedadDAO = personalEnfermedadDAO;
    }
    
    
}
