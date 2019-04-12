

package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteSeguroBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EstudianteSeguroService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class EstudianteSeguroMB extends BaseMB implements Serializable{

    /**
     * Creates a new instance of EstudianteSeguroMB
     */
    @PostConstruct
    public void init() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaCodigoBean=codigoService.obtenerPorTipo(new  TipoCodigoBean(MaristaConstantes.TIP_AMBIENTE));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private EstudianteSeguroBean estudianteSeguroBean;
    private List<EstudianteSeguroBean> listaEstudianteSeguroBean;
    private List<CodigoBean> listaCodigoBean;
    private List<EntidadBean> listaEntidadBean;
    private EstudianteBean estudianteBean;
    private EstudianteSeguroBean estudianteSeguroFiltroBean;
    private PersonaBean personaBean;

    //Logica de Negocio
    public void limpiarEstudianteSeguro() {
        estudianteSeguroBean = new EstudianteSeguroBean();
    }

    public void obtenerEstudianteSeguro() {
        try {
            EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
            listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguro();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarEstudianteSeguro() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin"); 
//                estudianteSeguroBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                estudianteSeguroBean.setCreaPor(usuarioBean.getUsuario());
                estudianteSeguroService.insertarEstudianteSeguro(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudianteSeguro() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                estudianteSeguroService.modificarEstudianteSeguro(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarEstudianteSeguro() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                estudianteSeguroService.eliminarEstudianteSeguro(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarEstudianteSeguro() {
        if (estudianteSeguroBean.getIdEstudianteSeguro() != null) {
            modificarEstudianteSeguro();
        } else {
            insertarEstudianteSeguro();
        }
    }

    public void rowSelectEstSeg(SelectEvent event) {
        try {
            estudianteSeguroBean = (EstudianteSeguroBean) event.getObject();
            EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
            estudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorId(estudianteSeguroBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstudianteSeguro(Object estudianteSeguro) {
        try {
            estudianteSeguroBean = (EstudianteSeguroBean) estudianteSeguro;
            EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
            estudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorId(estudianteSeguroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    public void limpiarEstudianteSeguroFiltro() {
        try {
            listaEstudianteSeguroBean = new ArrayList<>();
            estudianteSeguroFiltroBean = new EstudianteSeguroBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            getEstudianteSeguroFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
//    public void ponerEstudianteSeguro(Object persona) {
//        try {
//            personaBean = (PersonaBean) persona;
//            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
//            System.out.println("");
//            estudianteMB.getEstudianteBean().setPersonaBean(persona);
//            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
//            System.out.println("okokok");
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//    
    
    public void ponerEstudiante(Object persona){
        try {
            personaBean = (PersonaBean) persona;
            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
            estudianteMB.getEstudianteBean().setPersonaBean(personaBean);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    //Los Metodos Getter y Setter

    public EstudianteSeguroBean getEstudianteSeguroBean() {
        if(estudianteSeguroBean==null){
            estudianteSeguroBean = new EstudianteSeguroBean();
        }
        return estudianteSeguroBean;
    }

    public void setEstudianteSeguroBean(EstudianteSeguroBean estudianteSeguroBean) {
        this.estudianteSeguroBean = estudianteSeguroBean;
    }

    public List<EstudianteSeguroBean> getListaEstudianteSeguroBean() {
        if(listaEstudianteSeguroBean==null){
            listaEstudianteSeguroBean = new ArrayList<>();
        }
        return listaEstudianteSeguroBean;
    }

    public void setListaEstudianteSeguroBean(List<EstudianteSeguroBean> listaEstudianteSeguroBean) {
        this.listaEstudianteSeguroBean = listaEstudianteSeguroBean;
    }

    public List<CodigoBean> getListaCodigoBean() {
        if(listaCodigoBean==null){
            listaCodigoBean = new ArrayList<>();
        }
        return listaCodigoBean;
    }

    public void setListaCodigoBean(List<CodigoBean> listaCodigoBean) {
        this.listaCodigoBean = listaCodigoBean;
    } 

    public List<EntidadBean> getListaEntidadBean() {
        if(listaEntidadBean==null){
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
    }
    
    public EstudianteBean getEstudianteBean() {
        if(estudianteBean == null){
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public EstudianteSeguroBean getEstudianteSeguroFiltroBean() {
        if(estudianteSeguroFiltroBean == null){
            estudianteSeguroFiltroBean = new EstudianteSeguroBean();
        }
        return estudianteSeguroFiltroBean;
    }

    public void setEstudianteSeguroFiltroBean(EstudianteSeguroBean estudianteSeguroFiltroBean) {
        this.estudianteSeguroFiltroBean = estudianteSeguroFiltroBean;
    }

    public PersonaBean getPersonaBean() {
        if(personaBean == null){
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }
  
}
