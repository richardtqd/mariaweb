package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalProcesoJudicialBean;
import pe.marista.sigma.bean.reporte.PersonalProcesoJudicialRepBean;
import pe.marista.sigma.dao.PersonalProcesoJudicialDAO;

public class PersonalProcesoJudicialService {
    
    private PersonalProcesoJudicialDAO personalProcesoJudicialDAO;
    
    public List<PersonalProcesoJudicialBean> obtenerPersonalProcesoJudicialPorPersonal(PersonalBean personalBean) throws Exception{
        return  personalProcesoJudicialDAO.obtenerPersonalProcesoJudicialPorPersonal(personalBean);
    }
    public List<PersonalProcesoJudicialRepBean> obtenerProcesoJudicialPorPersonalRep(Integer idPersonal,String uniNeg) throws Exception{
        return  personalProcesoJudicialDAO.obtenerProcesoJudicialPorPersonalRep(idPersonal,uniNeg);
    }
    
    public PersonalProcesoJudicialBean obtenerPersonalProcesoJudicialPorId(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception{
        return personalProcesoJudicialDAO.obtenerPersonalProcesoJudicialPorId(personalProcesoJudicialBean);
    }
    
    //Logica de Negocio
    @Transactional
    public void insertarPersonalProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception{
       personalProcesoJudicialDAO.insertarPersonalProcesoJudicial(personalProcesoJudicialBean);
    }
    @Transactional
    public void modificarPersonalProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception{
        personalProcesoJudicialDAO.modificarPersonalProcesoJudicial(personalProcesoJudicialBean);
    }
    
    @Transactional
    public void eliminarPersonalProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception {
        personalProcesoJudicialDAO.eliminarPersonalProcesoJudicial(personalProcesoJudicialBean);
    }
    
    @Transactional
    public void cambiarEstadoProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception{
        personalProcesoJudicialDAO.cambiarEstadoProcesoJudicial(personalProcesoJudicialBean);
    }
        
    
    // metodos getter y setter

    public PersonalProcesoJudicialDAO getPersonalProcesoJudicialDAO() {
        return personalProcesoJudicialDAO;
    }

    public void setPersonalProcesoJudicialDAO(PersonalProcesoJudicialDAO personalProcesoJudicialDAO) {
        this.personalProcesoJudicialDAO = personalProcesoJudicialDAO;
    }
    
    
}
