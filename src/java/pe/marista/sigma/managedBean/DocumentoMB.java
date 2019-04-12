package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
 
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
 
import pe.marista.sigma.bean.DocumentoBean;
 
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
 
import pe.marista.sigma.service.DocumentoService;
 
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class DocumentoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DocumentoMB
     */
    @PostConstruct
    public void DocumentoMB() {
        try {
             beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
             
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoCatDocBean(); 
            listaTipoCatDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CAT_DOC));   
            getDocumentoBean().getTipoCatDocBean().setIdCodigo(17102);
           

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private DocumentoBean documentoBean;
    private List<DocumentoBean> listaDocumentoBean;
    private List<CodigoBean> listaTipoCatDocBean;
    
      private UsuarioBean beanUsuarioSesion;
    
    //Logica de Negocio
    public void limpiarDocumento() {
        documentoBean = new DocumentoBean();
        getDocumentoBean().getTipoCatDocBean().setIdCodigo(17102);
    
    }

    
    public void obtenerTodos() {
        try {
            DocumentoService documentoService = BeanFactory.getDocumentoService();
            listaDocumentoBean = documentoService.obtenerTodos();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//    public void obtenerTodosPorUniNeg() {
//        try {
//            DocumentoService documentoService = BeanFactory.getDocumentoService();
//            listaDocumentoBean = documentoService.obtenerTodosPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }

    public void obtenerPorId(Object object) {
        try {
            documentoBean = (DocumentoBean) object;
            DocumentoService documentoService = BeanFactory.getDocumentoService();
            documentoBean = documentoService.obtenerDocumentoPorId(documentoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarDocumento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DocumentoService documentoService = BeanFactory.getDocumentoService();
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                documentoBean.setCreaPor(beanUsuarioSesion.getUsuario());
//                documentoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                documentoService.insertarDocumento(documentoBean);
//                listaDocumentoBean = documentoService.obtenerTodosPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaDocumentoBean=documentoService.obtenerTodos();
                limpiarDocumento();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarDocumento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DocumentoService documentoService = BeanFactory.getDocumentoService();
                documentoBean.setModiPor(beanUsuarioSesion.getUsuario());
                documentoService.modificarDocumento(documentoBean);
//                listaDocumentoBean = documentoService.obtenerTodosPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaDocumentoBean=documentoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarDocumento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DocumentoService documentoService = BeanFactory.getDocumentoService();
                documentoService.eliminarDocumento(documentoBean);
//             listaDocumentoBean = documentoService.obtenerTodosPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaDocumentoBean=documentoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarDocumento() {
        if (documentoBean.getIdDocumento() == null) {
            insertarDocumento();
        } else {
            modificarDocumento();
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            documentoBean = (DocumentoBean) event.getObject();
            DocumentoService documentoService = BeanFactory.getDocumentoService();
            documentoBean = documentoService.obtenerDocumentoPorId(documentoBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public DocumentoBean getDocumentoBean() {
        if(documentoBean==null){
            documentoBean= new DocumentoBean();
        }
        return documentoBean;
    }

    public void setDocumentoBean(DocumentoBean documentoBean) {
        this.documentoBean = documentoBean;
    }

    public List<DocumentoBean> getListaDocumentoBean() {
        return listaDocumentoBean;
    }

    public void setListaDocumentoBean(List<DocumentoBean> listaDocumentoBean) {
        this.listaDocumentoBean = listaDocumentoBean;
    }

    public List<CodigoBean> getListaTipoCatDocBean() {
        if(listaTipoCatDocBean==null){
            listaTipoCatDocBean = new ArrayList<>();
        }
        return listaTipoCatDocBean;
    }

    public void setListaTipoCatDocBean(List<CodigoBean> listaTipoCatDocBean) {
        this.listaTipoCatDocBean = listaTipoCatDocBean;
    }

   
 

}
