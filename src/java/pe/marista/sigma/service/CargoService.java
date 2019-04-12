package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CargoBean;
import pe.marista.sigma.bean.CargoUniNegBean;
import pe.marista.sigma.bean.DocumentoBean;
import pe.marista.sigma.bean.DocumentoCargoBean;
import pe.marista.sigma.bean.UsuarioBean;

import pe.marista.sigma.dao.CargoDAO;
import pe.marista.sigma.dao.DocumentoCargoDAO;
import pe.marista.sigma.dao.DocumentoDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author Administrador
 */
public class CargoService {

    private CargoDAO cargoDAO;
    private DocumentoDAO documentoDAO;
    private DocumentoCargoDAO documentoCargoDAO;

    //Logica de Negocio
    @Transactional
    public void insertarCargo(CargoBean cargoBean, List<DocumentoBean> listaDocumentoOrig, DocumentoCargoBean documentoCargoBean,UsuarioBean usuarioBean) throws Exception {
        cargoDAO.insertarCargo(cargoBean);
        CargoUniNegService cargoUniNegService= BeanFactory.getCargoUniNegService();
        CargoUniNegBean cargoUniNegBean = new CargoUniNegBean();
        cargoUniNegBean.setCargoBean(cargoBean);
        cargoUniNegBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
        cargoUniNegBean.setCreaPor(cargoBean.getCreaPor());
        cargoUniNegService.insertarCargoUniNeg(cargoUniNegBean);
//        List<DocumentoBean> listaDocumentoBean = documentoDAO.obtenerTodos();
        for (Object objecto : listaDocumentoOrig) {
            DocumentoBean documentoBean = new DocumentoBean();
            documentoBean.setIdDocumento(new Integer(objecto.toString()));
            documentoBean = documentoDAO.obtenerDocumentoPorId(documentoBean);
            documentoCargoBean.setCargoBean(cargoBean);
            documentoCargoBean.setDocumentoBean(documentoBean);
            documentoCargoDAO.insertarDocumentoCargo(documentoCargoBean);
        }
    }

    @Transactional
    public void insertarSoloCargo(CargoBean cargoBean) throws Exception {
        cargoDAO.insertarCargo(cargoBean);
    }

    @Transactional
    public void eliminarCargo(Integer idCargo) throws Exception {
        documentoCargoDAO.eliminarDocumentoCargoDOC(idCargo);
        cargoDAO.eliminarCargo(idCargo);
    }

    @Transactional
    public void modificarCargo(CargoBean cargoBean, List<DocumentoBean> listaDocumentoOrig, List<DocumentoBean> listaDocumentoFuente) throws Exception {
        DocumentoCargoBean documentoCargoBean = new DocumentoCargoBean();
        cargoDAO.modificarCargo(cargoBean);

        List<DocumentoCargoBean> listaDocumentoObj = documentoCargoDAO.obtenerDocumentoPorCargo(cargoBean.getIdCargo());

        List<DocumentoCargoBean> listaDocCargoSession = new ArrayList();
        listaDocCargoSession = listaDocumentoObj;

        if (!listaDocumentoOrig.isEmpty()) {
            int estado = 0;
            for (Object objecto : listaDocumentoOrig) {
                DocumentoBean documentoBean = new DocumentoBean();
                DocumentoCargoBean documentoCargo = new DocumentoCargoBean();
                documentoBean.setIdDocumento(new Integer(objecto.toString()));
                documentoBean = documentoDAO.obtenerDocumentoPorId(documentoBean);
                documentoCargo.setCargoBean(cargoBean);
                documentoCargo.setDocumentoBean(documentoBean);
                documentoCargo = documentoCargoDAO.obtenerDocumentoCargoPorId(documentoCargo);
                if (documentoCargo == null) {
                    DocumentoCargoBean documentoCar = new DocumentoCargoBean();
                    documentoCar.setCargoBean(cargoBean);
                    documentoCar.setDocumentoBean(documentoBean);
                    documentoCargoDAO.insertarDocumentoCargo(documentoCar);
                } else {
                    Integer idDoc = null;
                    for (DocumentoCargoBean listaDocumentoObj1 : listaDocumentoObj) {
                        if (listaDocumentoObj1.getDocumentoBean().getIdDocumento().equals(new Integer(objecto.toString()))) {
                            estado = 1;
                            break;
                        } else {
                            idDoc = listaDocumentoObj1.getDocumentoBean().getIdDocumento();
                        }
                    }
                    if (estado == 0) {
                        DocumentoCargoBean doc = new DocumentoCargoBean();
                        doc.getDocumentoBean().setIdDocumento(idDoc);
                        doc.setCargoBean(cargoBean);
                        documentoCargoDAO.eliminarDocumentoCargo(doc);
                    }
                }
            }
            for (Object source : listaDocumentoFuente) {
                for (DocumentoCargoBean lista : listaDocumentoObj) {
                    if (lista.getDocumentoBean().getIdDocumento().equals(new Integer(source.toString()))) {
                        DocumentoCargoBean docu = new DocumentoCargoBean();
                        docu.getDocumentoBean().setIdDocumento(lista.getDocumentoBean().getIdDocumento());
                        docu.setCargoBean(cargoBean);
                        documentoCargoDAO.eliminarDocumentoCargo(docu);
                        break;
                    }
                }
            }
        } else {
            documentoCargoDAO.eliminarDocumentoCargoDOC(cargoBean.getIdCargo());
        }

    }

    @Transactional
    public void modificarSoloCargo(CargoBean cargoBean) throws Exception {
        cargoDAO.modificarCargo(cargoBean);
    }

    @Transactional
    public void cambiarEstado(CargoBean cargoBean) throws Exception {
        cargoDAO.cambiarEstado(cargoBean);
    }

    public List<CargoBean> obtenerTodos() throws Exception {
        return cargoDAO.obtenerTodos();
    }

    public List<CargoBean> obtenerTodosActivos() throws Exception {
        return cargoDAO.obtenerTodosActivos();
    }

    public CargoBean obtenerCargoPorId(Integer idCargo) throws Exception {
        return cargoDAO.obtenerCargoPorId(idCargo);
    }

    //Getter y Setter
    public CargoDAO getCargoDAO() {
        return cargoDAO;
    }

    public void setCargoDAO(CargoDAO CargoDAO) {
        this.cargoDAO = CargoDAO;
    }

    public DocumentoDAO getDocumentoDAO() {
        return documentoDAO;
    }

    public void setDocumentoDAO(DocumentoDAO documentoDAO) {
        this.documentoDAO = documentoDAO;
    }

    public DocumentoCargoDAO getDocumentoCargoDAO() {
        return documentoCargoDAO;
    }

    public void setDocumentoCargoDAO(DocumentoCargoDAO documentoCargoDAO) {
        this.documentoCargoDAO = documentoCargoDAO;
    }

    public String obtenerGrupoOcupacional(Integer idCodigo) throws Exception {
        return cargoDAO.obtenerGrupoOcupacional(idCodigo);
    }

}
