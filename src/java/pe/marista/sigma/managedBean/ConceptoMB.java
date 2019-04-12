
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class ConceptoMB extends BaseMB implements Serializable{

    /**
     * Creates a new instance of ConceptoMB
     */
    @PostConstruct
    public void init() {
        try {
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private ConceptoBean conceptoBean;
    private List<ConceptoBean> listaConceptoBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;

    public void limpiarConceptoBean() {
        conceptoBean = new ConceptoBean();
    }

    public String insertarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                conceptoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                conceptoService.insertarConcepto(conceptoBean);
                listaConceptoBean = conceptoService.obtenerConcepto();
                limpiarConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                conceptoService.modificarConcepto(conceptoBean);
                listaConceptoBean = conceptoService.obtenerConcepto();
                limpiarConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                conceptoService.eliminarConcepto(conceptoBean);
                listaConceptoBean = conceptoService.obtenerConcepto();
                limpiarConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerConceptoPorId(Object object) {
        try {
            conceptoBean = (ConceptoBean) object;
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            conceptoBean = conceptoService.obtenerConceptoPorId(conceptoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String guardarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (conceptoBean.getIdConcepto() != null) {
                    modificarConcepto();
                } else {
                    insertarConcepto();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerConcepto() {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            listaConceptoBean = conceptoService.obtenerConcepto();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                if (conceptoBean.getStatus()) {
                    conceptoBean.setStatus(Boolean.FALSE);
                } else {
                    conceptoBean.setStatus(Boolean.TRUE);
                }
                conceptoService.cambiarEstado(conceptoBean);
                listaConceptoBean = conceptoService.obtenerConcepto();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }


    public void rowSelectConcepto(SelectEvent event) {
        try {
            conceptoBean = (ConceptoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    public void ponerConcepto(Object bean) {
        try {
            conceptoBean = (ConceptoBean) bean;
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            conceptoBean = conceptoService.obtenerConceptoPorId(conceptoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Metodos Getter y Setter

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public List<ConceptoBean> getListaConceptoBean() {
        if (listaConceptoBean == null) {
            listaConceptoBean = new ArrayList<>();
        }
        return listaConceptoBean;
    }

    public void setListaConceptoBean(List<ConceptoBean> listaConceptoBean) {
        this.listaConceptoBean = listaConceptoBean;
    }

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

}
