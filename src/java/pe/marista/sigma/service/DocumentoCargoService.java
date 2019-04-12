package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CargoBean;
import pe.marista.sigma.bean.DocumentoBean;
import pe.marista.sigma.bean.DocumentoCargoBean;

import pe.marista.sigma.dao.DocumentoCargoDAO;

/**
 *
 * @author Administrador
 */
public class DocumentoCargoService {

    private DocumentoCargoDAO DocumentoCargoDAO;

    //Logica de Negocio

    @Transactional
    public void insertarDocumentoCargo(DocumentoCargoBean documentoCargoBean) throws Exception {
        DocumentoCargoDAO.insertarDocumentoCargo(documentoCargoBean);
    }

    @Transactional
    public void eliminarDocumentoCargo(DocumentoCargoBean documentoCargoBean) throws Exception {
        DocumentoCargoDAO.eliminarDocumentoCargo(documentoCargoBean);
    }
    @Transactional
    public void eliminarDocumentoCargoDOC(Integer idCargo) throws Exception {
        DocumentoCargoDAO.eliminarDocumentoCargoDOC(idCargo);
    }

    @Transactional
    public void modificarDocumentoCargo(DocumentoCargoBean documentoCargoBean) throws Exception {
        DocumentoCargoDAO.modificarDocumentoCargo(documentoCargoBean);
    }

    @Transactional
    public void cambiarEstados(DocumentoCargoBean documentoCargoBean) throws Exception {
        DocumentoCargoDAO.cambiarEstados(documentoCargoBean);
    }

    public List<DocumentoCargoBean> obtenerTodos() throws Exception {
        return DocumentoCargoDAO.obtenerTodos();
    }

//    public List<DocumentoCargoBean> obtenerTodosActivosPorUniNeg(String uniNeg) throws Exception {
//        return DocumentoCargoDAO.obtenerTodosActivosPorUniNeg(uniNeg);
//    }
//    public List<DocumentoCargoBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception {
//        return DocumentoCargoDAO.obtenerTodosPorUniNeg(uniNeg);
//    }

    public List<DocumentoCargoBean> obtenerTodosActivos() throws Exception {
        return DocumentoCargoDAO.obtenerTodosActivos();
    }

    public List<DocumentoCargoBean> obtenerPorCargo(CargoBean cargoBean) throws Exception {
        return DocumentoCargoDAO.obtenerPorCargo(cargoBean);
    }

    public List<DocumentoBean> obtenerDocPorCargo(Integer idCargo) throws Exception {
        return DocumentoCargoDAO.obtenerDocPorCargo(idCargo);
    }
    public List<DocumentoCargoBean> obtenerDocumentoPorCargo(Integer idCargo) throws Exception {
        return DocumentoCargoDAO.obtenerDocumentoPorCargo(idCargo);
    }

    public DocumentoCargoBean obtenerDocumentoCargoPorId(DocumentoCargoBean documentoCargoBean) throws Exception {
        return DocumentoCargoDAO.obtenerDocumentoCargoPorId(documentoCargoBean);
    }

    //Getter y Setter
    public DocumentoCargoDAO getDocumentoCargoDAO() {
        return DocumentoCargoDAO;
    }

    public void setDocumentoCargoDAO(DocumentoCargoDAO DocumentoCargoDAO) {
        this.DocumentoCargoDAO = DocumentoCargoDAO;
    }

}
