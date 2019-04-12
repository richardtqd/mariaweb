package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class ConceptoUniNegMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ConceptoUniNegMB
     */
    @PostConstruct
    public void ConceptoUniNegMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();

            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaConceptoBean();
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            listaConceptoBean = conceptoService.obtenerConceptoNotIn(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
             CodigoService codigoService = BeanFactory.getCodigoService();
            listaMoneda = new ArrayList<>();
            listaMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private ConceptoUniNegBean conceptoUniNegBean;
    private List<ConceptoBean> listaConceptoBean;
    private UsuarioBean usuarioLoginBean;
    private List<CodigoBean> listaMoneda;

    //Metodos Logica
    public void limpiarConceptoUniNegBean() {
        try {
            conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            listaConceptoBean = conceptoService.obtenerConceptoNotIn(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarConceptoUniNeg() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoUniNegBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                conceptoUniNegBean.setCreaPor(usuarioLoginBean.getUsuario());
                conceptoUniNegService.insertarConceptoUniNeg(conceptoUniNegBean);
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarConceptoUniNegBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarConceptoUniNeg() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                conceptoUniNegBean.setModiPor(beanUsuarioSesion.getUsuario());
                conceptoUniNegService.modificarConceptoUniNeg(conceptoUniNegBean);

                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarConceptoUniNegBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarConceptoUniNeg() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoUniNegService.eliminarConceptoUniNeg(conceptoUniNegBean);
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarConceptoUniNegBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarConceptoUniNeg() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (conceptoUniNegBean.getUnidadNegocioBean().getUniNeg() != null) {
                    modificarConceptoUniNeg();
                } else {
                    insertarConceptoUniNeg();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerTodos() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object object) {
        try {
            conceptoUniNegBean = (ConceptoUniNegBean) object;
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerConceptoUniNeg(Object categoria) {
        try {
            conceptoUniNegBean = (ConceptoUniNegBean) categoria;
        } catch (Exception err) {

            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            listaConceptoBean = conceptoService.obtenerConcepto();
            conceptoUniNegBean = (ConceptoUniNegBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                if (conceptoUniNegBean.getStatus()) {
                    conceptoUniNegBean.setStatus(Boolean.FALSE);
                } else {
                    conceptoUniNegBean.setStatus(Boolean.TRUE);
                }
                conceptoUniNegService.cambiarEstadoConceptoUniNeg(conceptoUniNegBean);
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //Metodos Getter y Setter
    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
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

    public List<CodigoBean> getListaMoneda() {
        if (listaMoneda==null) {
            listaMoneda= new ArrayList<>();
        }
        return listaMoneda;
    }

    public void setListaMoneda(List<CodigoBean> listaMoneda) {
        this.listaMoneda = listaMoneda;
    }

}
