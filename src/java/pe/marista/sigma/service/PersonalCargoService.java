package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DocumentoBean;
import pe.marista.sigma.bean.DocumentoCargoBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalCargoBean;
import pe.marista.sigma.bean.PersonalDocumentoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.DocumentoCargoDAO;
import pe.marista.sigma.dao.DocumentoDAO;
import pe.marista.sigma.dao.PersonalCargoDAO;
import pe.marista.sigma.dao.PersonalDocumentoDAO;
import pe.marista.sigma.bean.reporte.PersonalCargoRepBean;

public class PersonalCargoService {

    private PersonalCargoDAO personalCargoDAO;
    private DocumentoCargoDAO documentoCargoDAO;
    private DocumentoDAO documentoDAO;
    private PersonalDocumentoDAO personalDocumentoDAO;

    public List<PersonalCargoBean> obtenerPersonalCargoPorPersonal(PersonalBean personalBean) throws Exception {
        return personalCargoDAO.obtenerPersonalCargoPorPersonal(personalBean);
    }
    
    public List<PersonalCargoRepBean> obtenerCargoPorPersonalRep(Integer idPersonal, String uniNeg) throws Exception {
           return personalCargoDAO.obtenerCargoPorPersonalRep(idPersonal,uniNeg);
    }

    public PersonalCargoBean obtenerPersonalCargoPorId(PersonalCargoBean personalCargoBean) throws Exception {
        return personalCargoDAO.obtenerPersonalCargoPorId(personalCargoBean);
    }

    public PersonalCargoBean obtenerCargoActivoPorPersonal(PersonalBean personalBean) throws Exception {
        return personalCargoDAO.obtenerCargoActivoPorPersonal(personalBean);
    }

    //Logica de Negocio
    @Transactional
    public void insertarPersonalCargo(PersonalCargoBean personalCargoBean, List<DocumentoCargoBean> listaDocumentoCargo, PersonalDocumentoBean personalDocumentoBean, UsuarioBean beanUsuarioSesion) throws Exception {
        personalCargoDAO.insertarPersonalCargo(personalCargoBean);
        if (listaDocumentoCargo != null) {
            if (!listaDocumentoCargo.isEmpty()) {
                for (DocumentoCargoBean documento : listaDocumentoCargo) {
                    DocumentoCargoBean documentoCargoBean = new DocumentoCargoBean();
                    documentoCargoBean.getCargoBean().setIdCargo(personalCargoBean.getCargoBean().getIdCargo());
                    documentoCargoBean.getDocumentoBean().setIdDocumento(documento.getDocumentoBean().getIdDocumento());
                    documentoCargoBean = documentoCargoDAO.obtenerDocumentoCargoPorId(documentoCargoBean);
                    personalDocumentoBean.getPersonalBean().setIdPersonal(personalCargoBean.getPersonalBean().getIdPersonal());
                    personalDocumentoBean.setDocumentoBean(documentoCargoBean.getDocumentoBean());
                    personalDocumentoBean.setFlgCaduca(documentoCargoBean.getDocumentoBean().isFlgCaduca());
                    personalDocumentoBean.getTipoCopiaBean().setIdCodigo(documentoCargoBean.getTipoCopiaBean().getIdCodigo());
                    personalDocumentoBean.setFlgObligatorio(documentoCargoBean.getFlgObligatorio());
                    personalDocumentoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                    personalDocumentoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    personalDocumentoDAO.insertarPersonalDocumento(personalDocumentoBean);
                }
            }
        }
    }

    @Transactional
    public void modificarPersonalCargo(PersonalCargoBean personalCargoBean) throws Exception {
        personalCargoDAO.modificarPersonalCargo(personalCargoBean);
    }

    @Transactional
    public void eliminarPersonalCargo(PersonalCargoBean personalCargoBean) throws Exception {
        personalCargoDAO.eliminarPersonalCargo(personalCargoBean);
    }

    @Transactional
    public void cambiarEstadoCargo(PersonalCargoBean personalCargoBean) throws Exception {
        personalCargoDAO.cambiarEstadoCargo(personalCargoBean);
    }

    // metodos getter y setter
    public PersonalCargoDAO getPersonalCargoDAO() {
        return personalCargoDAO;
    }

    public void setPersonalCargoDAO(PersonalCargoDAO personalCargoDAO) {
        this.personalCargoDAO = personalCargoDAO;
    }

    public DocumentoCargoDAO getDocumentoCargoDAO() {
        return documentoCargoDAO;
    }

    public void setDocumentoCargoDAO(DocumentoCargoDAO documentoCargoDAO) {
        this.documentoCargoDAO = documentoCargoDAO;
    }

    public PersonalDocumentoDAO getPersonalDocumentoDAO() {
        return personalDocumentoDAO;
    }

    public void setPersonalDocumentoDAO(PersonalDocumentoDAO personalDocumentoDAO) {
        this.personalDocumentoDAO = personalDocumentoDAO;
    }

    public DocumentoDAO getDocumentoDAO() {
        return documentoDAO;
    }

    public void setDocumentoDAO(DocumentoDAO documentoDAO) {
        this.documentoDAO = documentoDAO;
    }

}
