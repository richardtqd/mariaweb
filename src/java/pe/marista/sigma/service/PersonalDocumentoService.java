package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDocumentoBean;
import pe.marista.sigma.bean.PersonalPDFBean;
import pe.marista.sigma.bean.reporte.PersonalDocumentoRepBean;
import pe.marista.sigma.dao.PersonalDocumentoDAO;


public class PersonalDocumentoService {
    
    private PersonalDocumentoDAO personalDocumentoDAO;
    
    public List<PersonalDocumentoBean> obtenerPersonalDocumentoPorPersonal(PersonalBean personalBean) throws Exception{
        return  personalDocumentoDAO.obtenerPersonalDocumentoPorPersonal(personalBean);
    }
    
    public List<PersonalDocumentoRepBean> obtenerDocumentoPorPersonalRep(Integer idPersonal,String uniNeg) throws Exception{
        return  personalDocumentoDAO.obtenerDocumentoPorPersonalRep(idPersonal,uniNeg);
    }
    
    public PersonalDocumentoBean obtenerPersonalDocumentoPorId(PersonalDocumentoBean personalDocumentoBean) throws Exception{
        return personalDocumentoDAO.obtenerPersonalDocumentoPorId(personalDocumentoBean);
    }
    
    //Logica de Negocio
    @Transactional
    public void insertarPersonalDocumento(PersonalDocumentoBean personalDocumentoBean) throws Exception{
       personalDocumentoDAO.insertarPersonalDocumento(personalDocumentoBean);
    }
    @Transactional
    public void modificarPersonalDocumento(PersonalDocumentoBean personalDocumentoBean) throws Exception{
        personalDocumentoDAO.modificarPersonalDocumento(personalDocumentoBean);
    }
    
    @Transactional
    public void eliminarPersonalDocumento(PersonalDocumentoBean personalDocumentoBean) throws Exception {
        personalDocumentoDAO.eliminarPersonalDocumento(personalDocumentoBean);
    }
          
    
    // metodos getter y setter
    public PersonalDocumentoDAO getPersonalDocumentoDAO() {
        return personalDocumentoDAO;
    }

    public void setPersonalDocumentoDAO(PersonalDocumentoDAO personalDocumentoDAO) {
        this.personalDocumentoDAO = personalDocumentoDAO;
    }

    public void insertarPersonalPDF(PersonalPDFBean personalPDFBean) throws Exception {
        personalDocumentoDAO.insertarPersonalPDF(personalPDFBean);
    }

    public List<PersonalPDFBean> obtenerPersonalPDFPorPersonal(PersonalBean personalBean) throws Exception {
        return personalDocumentoDAO.obtenerPersonalPDFPorPersonal(personalBean);
    }
    
    
}
