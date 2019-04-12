/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.DetraccionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.DetraccionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class DetraccionMB implements Serializable{

    /**
     * Creates a new instance of DetraccionMB
     */
    @PostConstruct
    public void DetraccionMB() {
        try {
            //sesion del usuario
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            getListaDetraccionBean();
            listaDetraccionBean = detraccionService.obtenerTodos();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarDetraccion() {
        detraccionBean = new DetraccionBean();
    }

    public String insertarDetraccion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DetraccionService detraccionService = BeanFactory.getDetraccionService();
                detraccionBean.setCreaPor(usuarioLoginBean.getUsuario());
                detraccionService.insertarDetraccion(detraccionBean);
                listaDetraccionBean = detraccionService.obtenerTodos();
                limpiarDetraccion();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarDetraccion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DetraccionService detraccionService = BeanFactory.getDetraccionService();
                detraccionBean.setModiPor(usuarioLoginBean.getUsuario());
                detraccionService.modificarDetraccion(detraccionBean);
                listaDetraccionBean = detraccionService.obtenerTodos();
                limpiarDetraccion();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarDetraccion() {
        try {
            if (detraccionBean.getIdDetraccion() == null) {
                insertarDetraccion();
            } else {
                modificarDetraccion();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public String eliminarDetraccion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DetraccionService detraccionService = BeanFactory.getDetraccionService();
                detraccionService.eliminarDetraccion(detraccionBean);
                listaDetraccionBean = detraccionService.obtenerTodos();
                limpiarDetraccion();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoBeca() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DetraccionService detraccionService = BeanFactory.getDetraccionService();
                detraccionService.cambiarEstadoDetraccion(detraccionBean);
                listaDetraccionBean = detraccionService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

     public void obtenerPorId(Object objeto) {
        try {
            detraccionBean = (DetraccionBean) objeto;
            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            detraccionBean = detraccionService.obtenerPorId(detraccionBean);

            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            detraccionBean = new DetraccionBean();
            detraccionBean = (DetraccionBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Varialbes y Propiedades
    private DetraccionBean detraccionBean;
    private List<DetraccionBean> listaDetraccionBean;
    private UsuarioBean usuarioLoginBean;

    public DetraccionBean getDetraccionBean() {
        if (detraccionBean == null) {
            detraccionBean = new DetraccionBean();
        }
        return detraccionBean;
    }

    public void setDetraccionBean(DetraccionBean detraccionBean) {
        this.detraccionBean = detraccionBean;
    }

    public List<DetraccionBean> getListaDetraccionBean() {
        if (listaDetraccionBean == null) {
            listaDetraccionBean = new ArrayList<>();
        }
        return listaDetraccionBean;
    }

    public void setListaDetraccionBean(List<DetraccionBean> listaDetraccionBean) {
        this.listaDetraccionBean = listaDetraccionBean;
    }

}
