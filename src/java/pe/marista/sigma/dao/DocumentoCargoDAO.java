package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CargoBean;
import pe.marista.sigma.bean.DocumentoBean;
import pe.marista.sigma.bean.DocumentoCargoBean;

public interface DocumentoCargoDAO {
    
    public void insertarDocumentoCargo(DocumentoCargoBean documentoCargoBean) throws Exception;
    public void modificarDocumentoCargo(DocumentoCargoBean documentoCargoBean) throws Exception;
    public void eliminarDocumentoCargo(DocumentoCargoBean documentoCargoBean) throws Exception;
    public void eliminarDocumentoCargoDOC(Integer idCargo) throws Exception;//elimnar dependecia en cargo
    public void cambiarEstados(DocumentoCargoBean documentoCargoBean) throws Exception;
    public List<DocumentoCargoBean> obtenerTodos() throws Exception;
    public List<DocumentoCargoBean> obtenerTodosActivos() throws Exception;
//    public List<DocumentoCargoBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception;
//    public List<DocumentoCargoBean> obtenerTodosActivosPorUniNeg(String uniNeg) throws Exception;
    public List<DocumentoCargoBean> obtenerPorCargo(CargoBean cargoBean) throws Exception;
    public DocumentoCargoBean obtenerDocumentoCargoPorId(DocumentoCargoBean documentoCargoBean) throws Exception;
    
    public List<DocumentoBean> obtenerDocPorCargo(Integer idCargo) throws Exception;//obtengo los documento por cargo de la tabla Documento
    public List<DocumentoCargoBean> obtenerDocumentoPorCargo(Integer idCargo) throws Exception;//obtengo los documento por cargo de la tabla DocumentoCargo
//    public List<DocumentoCargoBean> obtenerCargoPorDoc(Integer idDocumento) throws Exception;
  
}
