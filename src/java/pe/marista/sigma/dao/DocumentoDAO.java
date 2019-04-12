package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.DocumentoBean;

public interface DocumentoDAO {
    
    public void insertarDocumento(DocumentoBean documentoBean) throws Exception;

    public void modificarDocumento(DocumentoBean documentoBean) throws Exception;

    public void eliminarDocumento(DocumentoBean documentoBean) throws Exception;
    
     public void cambiarEstados(DocumentoBean documentoBean) throws Exception;
    
    public List<DocumentoBean> obtenerTodos() throws Exception;
    
     public List<DocumentoBean> obtenerTodosActivos() throws Exception;
    
//    public List<DocumentoBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception;
 
//    public List<DocumentoBean> obtenerTodosActivosPorUniNeg(String uniNeg) throws Exception;

    public DocumentoBean obtenerDocumentoPorId(DocumentoBean documentoBean) throws Exception; 
         
}
