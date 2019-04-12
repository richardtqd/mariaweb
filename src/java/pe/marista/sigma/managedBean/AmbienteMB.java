package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AmbienteBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AmbienteService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class AmbienteMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of AmbienteMB
     */
    @PostConstruct
    public void AmbienteMB() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaCodigoBean=codigoService.obtenerPorTipo(new  TipoCodigoBean(MaristaConstantes.TIP_AMBIENTE));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Variables y Propiedades
    private AmbienteBean ambienteBean;
    private List<AmbienteBean> listaAmbienteBean;
    private List<CodigoBean> listaCodigoBean;

    public void limpiarAmbiente() {
        ambienteBean = new AmbienteBean();
    }

    public void obtenerAmbiente() {
        try {
            AmbienteService ambienteService = BeanFactory.getAmbienteService();
             UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
             listaAmbienteBean = ambienteService.obtenerAmbientePorUnidadNeg((beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarAmbiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AmbienteService ambienteService = BeanFactory.getAmbienteService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ambienteBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                ambienteBean.setCreaPor(usuarioBean.getUsuario());
                ambienteService.insertarAmbiente(ambienteBean);
                 listaAmbienteBean = ambienteService.obtenerAmbientePorUnidadNeg((usuarioBean.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarAmbiente();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarAmbiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AmbienteService ambienteService = BeanFactory.getAmbienteService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ambienteBean.setModiPor(usuarioBean.getUsuario());
                ambienteService.modificarAmbiente(ambienteBean);
                listaAmbienteBean = ambienteService.obtenerAmbientePorUnidadNeg((usuarioBean.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarAmbiente();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarAmbiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AmbienteService ambienteService = BeanFactory.getAmbienteService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ambienteService.eliminarAmbiente(ambienteBean.getIdAmbiente());
                 listaAmbienteBean = ambienteService.obtenerAmbientePorUnidadNeg((usuarioBean.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarAmbiente() {
        if (ambienteBean.getIdAmbiente() != null) {
            modificarAmbiente();
        } else {
            insertarAmbiente();
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            ambienteBean = (AmbienteBean) event.getObject();
            AmbienteService ambienteService = BeanFactory.getAmbienteService();
            ambienteBean = ambienteService.obtenerAmbientePorId(ambienteBean.getIdAmbiente());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerAmbiente(Object ambiente) {
        try {
            ambienteBean = (AmbienteBean) ambiente;
            AmbienteService ambienteService = BeanFactory.getAmbienteService();
            ambienteBean = ambienteService.obtenerAmbientePorId(ambienteBean.getIdAmbiente());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Metodos Getter y Setter
    public AmbienteBean getAmbienteBean() {
        if (ambienteBean == null) {
            ambienteBean = new AmbienteBean();
        }
        return ambienteBean;
    }

    public void setAmbienteBean(AmbienteBean ambienteBean) {
        this.ambienteBean = ambienteBean;
    }

    public List<AmbienteBean> getListaAmbienteBean() {
        if (listaAmbienteBean == null) {
            listaAmbienteBean = new ArrayList<>();
        }
        return listaAmbienteBean;
    }

    public void setListaAmbienteBean(List<AmbienteBean> listaAmbienteBean) {
        this.listaAmbienteBean = listaAmbienteBean;
    }
    public List<CodigoBean> getListaCodigoBean() {
        if (listaCodigoBean == null) {
            listaCodigoBean = new ArrayList<>();
        }
        return listaCodigoBean;
    }

    public void setListaCodigoBean(List<CodigoBean> listaCodigoBean) {
        this.listaCodigoBean = listaCodigoBean;
    }

}
