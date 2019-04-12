package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalExperienciaBean;
import pe.marista.sigma.bean.reporte.PersonalExperienciaRepBean;
import pe.marista.sigma.dao.PersonalExperienciaDAO;

/**
 *
 * @author MS-005
 */
public class PersonalExperienciaService {
    
    private PersonalExperienciaDAO personalExperienciaDAO;
    
    public List<PersonalExperienciaBean> obtenerPersonalExperienciaPorPersonal(PersonalBean personalBean) throws Exception{
        return  personalExperienciaDAO.obtenerPersonalExperienciaPorPersonal(personalBean);
    }
    public List<PersonalExperienciaRepBean> obtenerExperienciaPorPersonalRep(Integer idPersonal,String uniNeg) throws Exception{
        return  personalExperienciaDAO.obtenerExperienciaPorPersonalRep(idPersonal,uniNeg);
    }
    
    public PersonalExperienciaBean obtenerPersonalExperienciaPorId(PersonalExperienciaBean personalExperienciaBean) throws Exception{
        return personalExperienciaDAO.obtenerPersonalExperienciaPorId(personalExperienciaBean);
    }
    
    //Logica de Negocio
    @Transactional
    public void insertarPersonalExperiencia(PersonalExperienciaBean personalExperienciaBean) throws Exception{
       personalExperienciaDAO.insertarPersonalExperiencia(personalExperienciaBean);
    }
    @Transactional
    public void modificarPersonalExperiencia(PersonalExperienciaBean personalExperienciaBean) throws Exception{
        personalExperienciaDAO.modificarPersonalExperiencia(personalExperienciaBean);
    }
    
    @Transactional
    public void eliminarPersonalExperiencia(PersonalExperienciaBean personalExperienciaBean) throws Exception {
        personalExperienciaDAO.eliminarPersonalExperiencia(personalExperienciaBean);
    }
     
    // metodos getter y setter

    public PersonalExperienciaDAO getPersonalExperienciaDAO() {
        return personalExperienciaDAO;
    }

    public void setPersonalExperienciaDAO(PersonalExperienciaDAO personalExperienciaDAO) {
        this.personalExperienciaDAO = personalExperienciaDAO;
    }
    
    
}
