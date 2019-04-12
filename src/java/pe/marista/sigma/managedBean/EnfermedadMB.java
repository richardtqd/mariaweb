package pe.marista.sigma.managedBean;

import java.io.Serializable;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EnfermedadBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EnfermedadService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class EnfermedadMB implements Serializable{

    @PostConstruct
    public void EnfermedadMB() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEnfermedadBean();
            listaTipoEnfermedadBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ENFERMEDAD));
            
//            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private EnfermedadBean enfermedadBean;
    private List<EnfermedadBean> listaEnfermedadBean;
    private PersonalBean personalBean;
    private List<CodigoBean> listaTipoEnfermedadBean;
    
//     private UsuarioBean usuarioLoginBean;

    public void obtenerEnfermedad() {
        try {
            EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
            listaEnfermedadBean = enfermedadService.obtenerEnfermedad();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEnfermedadPorId(Object object) {
        try {
            enfermedadBean = (EnfermedadBean) object;
            EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
            enfermedadBean = enfermedadService.obtenerEnfermedadPorId(getEnfermedadBean().getIdEnfermedad());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    

    public void limpiarEnfermedad() {
        enfermedadBean = new EnfermedadBean();
    }

    public String insertarEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                enfermedadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                enfermedadService.insertarEnfermedad(enfermedadBean);
                listaEnfermedadBean = enfermedadService.obtenerEnfermedad();
                limpiarEnfermedad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                enfermedadBean.setModiPor(beanUsuarioSesion.getUsuario());
                enfermedadService.modificarEnfermedad(enfermedadBean);
                listaEnfermedadBean = enfermedadService.obtenerEnfermedad();
                limpiarEnfermedad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
                enfermedadService.eliminarEnfermedad(enfermedadBean.getIdEnfermedad());
               listaEnfermedadBean = enfermedadService.obtenerEnfermedad();
                limpiarEnfermedad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void RowSelect(SelectEvent event) {
        try {
            enfermedadBean = (EnfermedadBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
     public void guardarEnfermedad() {

        try {
            if (enfermedadBean.getIdEnfermedad() == null) {
                insertarEnfermedad();
            } else {
                modificarEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

// private Integer idEnfermedad;
//    private String enfermedad;
//    private CodigoBean tipoEnfermedadBean;
    // public void eliminarEnfermedad(Integer idEnfermedad) throws Exception;
    // public List<EnfermedadBean> obtenerEnfermedadPorTipo(Integer idTipoEnfermedad) throws Exception;
    //Metodos Getter y Setter
    public EnfermedadBean getEnfermedadBean() {
        
    if(enfermedadBean == null)
        {
            enfermedadBean = new EnfermedadBean();
        }
        return enfermedadBean;
  
    }

    public void setEnfermedadBean(EnfermedadBean enfermedadBean) {
        this.enfermedadBean = enfermedadBean;
    }

    public List<EnfermedadBean> getListaEnfermedadBean() {
        
        if(listaEnfermedadBean == null)
        {
            listaEnfermedadBean = new ArrayList<>();
        }
        return listaEnfermedadBean;

    }

    public void setListaEnfermedadBean(List<EnfermedadBean> listaEnfermedadBean) {
        this.listaEnfermedadBean = listaEnfermedadBean;
    }

    public PersonalBean getPersonalBean() {
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public List<CodigoBean> getListaTipoEnfermedadBean() {
        
        if(listaTipoEnfermedadBean == null)
        {
            listaTipoEnfermedadBean = new ArrayList<>();
        }
        return listaTipoEnfermedadBean;
    }

    public void setListaTipoEnfermedadBean(List<CodigoBean> listaTipoEnfermedadBean) {
        this.listaTipoEnfermedadBean = listaTipoEnfermedadBean;
    }
}
