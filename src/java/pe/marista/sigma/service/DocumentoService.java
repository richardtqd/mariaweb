 

package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DocumentoBean;
 
import pe.marista.sigma.dao.DocumentoDAO;

/**
 *
 * @author Administrador
 */
public class DocumentoService {
    private DocumentoDAO DocumentoDAO;
    //Logica de Negocio
    @Transactional
    public void insertarDocumento(DocumentoBean documentoBean) throws Exception{
        DocumentoDAO.insertarDocumento(documentoBean);
    }
    @Transactional
    public void eliminarDocumento(DocumentoBean documentoBean) throws Exception{
        DocumentoDAO.eliminarDocumento(documentoBean);
    }
    @Transactional
    public void modificarDocumento(DocumentoBean documentoBean) throws Exception{
        DocumentoDAO.modificarDocumento(documentoBean);
    }
    @Transactional
    public void cambiarEstados(DocumentoBean documentoBean) throws Exception{
        DocumentoDAO.cambiarEstados(documentoBean);
    }
    
//    public List<DocumentoBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception{
//       return DocumentoDAO.obtenerTodosPorUniNeg(uniNeg);
//    }
//    public List<DocumentoBean> obtenerTodosActivosPorUniNeg(String uniNeg) throws Exception{
//       return DocumentoDAO.obtenerTodosActivosPorUniNeg(uniNeg);
//    }
    public List<DocumentoBean> obtenerTodos() throws Exception{
       return DocumentoDAO.obtenerTodos();
    }
    public List<DocumentoBean> obtenerTodosActivos() throws Exception{
       return DocumentoDAO.obtenerTodosActivos();
    }
       
    public DocumentoBean obtenerDocumentoPorId(DocumentoBean documentoBean) throws Exception{
       return DocumentoDAO.obtenerDocumentoPorId(documentoBean);
    }
       
    //Getter y Setter
    public DocumentoDAO getDocumentoDAO() {
        return DocumentoDAO;
    }

    public void setDocumentoDAO(DocumentoDAO DocumentoDAO) {
        this.DocumentoDAO = DocumentoDAO;
    }
    
}
