package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CargoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocumentoBean;
import pe.marista.sigma.bean.DocumentoCargoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CargoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DocumentoCargoService;
import pe.marista.sigma.service.DocumentoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class DocumentoCargoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DocumentoCargoMB
     */
    @PostConstruct
    public void DocumentoCargoMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            CargoService cargoService = BeanFactory.getCargoService();
            getListaCargoBean();
            listaCargoBean = cargoService.obtenerTodos();
            DocumentoService documentoService = BeanFactory.getDocumentoService();
            getListaDocumentoBean();
            listaDocumentoBean = documentoService.obtenerTodos();

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoCopiaBean();
            listaTipoCopiaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_COPIA));

            getListaCatDocBean();
            listaCatDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CAT_DOC));

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private DocumentoCargoBean documentoCargoBean;
    private List<DocumentoCargoBean> listaDocumentoCargoBean;
    private CargoBean cargoBean;
    private List<CargoBean> listaCargoBean;
    private DocumentoBean documentoBean;
    private List<DocumentoBean> listaDocumentoBean;
    private Map<String, Integer> listaFlgOriginal;
    private List<CodigoBean> listaTipoCopiaBean;
    private List<CodigoBean> listaCatDocBean;
    private UsuarioBean beanUsuarioSesion;

    //Logica de Negocio
    public void listaFlgOriginal() {
        listaFlgOriginal = new LinkedHashMap<>();
        listaFlgOriginal.put(MensajesBackEnd.getValueOfKey("etiquetaCopiaSim", null), 0);
        listaFlgOriginal.put(MensajesBackEnd.getValueOfKey("etiquetaOriginal", null), 1);
        listaFlgOriginal.put(MensajesBackEnd.getValueOfKey("etiquetaCopiaCer", null), 2);
        listaFlgOriginal = Collections.unmodifiableMap(listaFlgOriginal);
    }

    public void limpiarDocumentoCargo() {
        documentoCargoBean = new DocumentoCargoBean();
    }

    public void obtenerTodos() {
        try {
            DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
            listaDocumentoCargoBean = documentoCargoService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//     tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
//                    bean.setTipoEstadoBean(tipoEstadoBean); 

    public void obtenerPorId(Object object) {
        try {
            documentoCargoBean = (DocumentoCargoBean) object;
            DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
            documentoCargoBean = documentoCargoService.obtenerDocumentoCargoPorId(documentoCargoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarDocumentoCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                documentoCargoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                documentoCargoService.insertarDocumentoCargo(documentoCargoBean);
                listaDocumentoCargoBean = documentoCargoService.obtenerTodos();
                limpiarDocumentoCargo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarDocumentoCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
                documentoCargoService.modificarDocumentoCargo(documentoCargoBean);
                listaDocumentoCargoBean = documentoCargoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarDocumentoCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
                documentoCargoService.eliminarDocumentoCargo(documentoCargoBean);
                listaDocumentoCargoBean = documentoCargoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarDocumentoCargo() {
        if (documentoCargoBean.getCargoBean().getIdCargo() == null && documentoCargoBean.getDocumentoBean().getIdDocumento() == null) {
            insertarDocumentoCargo();
        } else {
            modificarDocumentoCargo();
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
            documentoCargoBean = (DocumentoCargoBean) event.getObject();
            documentoCargoBean = documentoCargoService.obtenerDocumentoCargoPorId(documentoCargoBean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public DocumentoCargoBean getDocumentoCargoBean() {
        if (documentoCargoBean == null) {
            documentoCargoBean = new DocumentoCargoBean();
        }
        return documentoCargoBean;
    }

    public void setDocumentoCargoBean(DocumentoCargoBean documentoCargoBean) {
        this.documentoCargoBean = documentoCargoBean;
    }

    public List<DocumentoCargoBean> getListaDocumentoCargoBean() {
        if (listaDocumentoCargoBean == null) {
            listaDocumentoCargoBean = new ArrayList<>();
        }
        return listaDocumentoCargoBean;
    }

    public void setListaDocumentoCargoBean(List<DocumentoCargoBean> listaDocumentoCargoBean) {
        this.listaDocumentoCargoBean = listaDocumentoCargoBean;
    }

    public CargoBean getCargoBean() {

        if (cargoBean == null) {
            cargoBean = new CargoBean();
        }
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public List<CargoBean> getListaCargoBean() {
        if (listaCargoBean == null) {
            listaCargoBean = new ArrayList<>();
        }
        return listaCargoBean;
    }

    public void setListaCargoBean(List<CargoBean> listaCargoBean) {
        this.listaCargoBean = listaCargoBean;
    }

    public DocumentoBean getDocumentoBean() {
        if (documentoBean == null) {
            documentoBean = new DocumentoBean();
        }
        return documentoBean;
    }

    public void setDocumentoBean(DocumentoBean documentoBean) {
        this.documentoBean = documentoBean;
    }

    public List<DocumentoBean> getListaDocumentoBean() {
        if (listaDocumentoBean == null) {
            listaDocumentoBean = new ArrayList<>();
        }
        return listaDocumentoBean;
    }

    public void setListaDocumentoBean(List<DocumentoBean> listaDocumentoBean) {
        this.listaDocumentoBean = listaDocumentoBean;
    }

    public Map<String, Integer> getListaFlgOriginal() {
        return listaFlgOriginal;
    }

    public void setListaFlgOriginal(Map<String, Integer> listaFlgOriginal) {
        this.listaFlgOriginal = listaFlgOriginal;
    }

    public List<CodigoBean> getListaTipoCopiaBean() {
        return listaTipoCopiaBean;
    }

    public void setListaTipoCopiaBean(List<CodigoBean> listaTipoCopiaBean) {
        this.listaTipoCopiaBean = listaTipoCopiaBean;
    }

    public List<CodigoBean> getListaCatDocBean() {
        if (listaCatDocBean == null) {
            listaCatDocBean = new ArrayList<>();
        }
        return listaCatDocBean;
    }

    public void setListaCatDocBean(List<CodigoBean> listaCatDocBean) {
        this.listaCatDocBean = listaCatDocBean;
    }

}
