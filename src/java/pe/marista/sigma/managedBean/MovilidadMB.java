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
import pe.marista.sigma.bean.MovilidadBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.MovilidadService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class MovilidadMB  extends BaseMB implements Serializable{

    /**
     * Creates a new instance of MovilidadMB
     */
    @PostConstruct
    public void MovilidadMB() {
        try {
            ObtenerMovilidad();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private MovilidadBean movilidadBean;
    private List<MovilidadBean> listaMovilidadBean;

    public void ObtenerMovilidad() {
        try {
            MovilidadService movilidadService = BeanFactory.getMovilidadService();
            listaMovilidadBean = movilidadService.ObtenerMovilidad();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String InsertarMovilidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MovilidadService movilidadService = BeanFactory.getMovilidadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                movilidadBean.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                movilidadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                movilidadService.InsertarMovilidad(movilidadBean);
                listaMovilidadBean = movilidadService.ObtenerMovilidad();
                limpiarMovilidad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String EliminarMovilidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MovilidadService movilidadService = BeanFactory.getMovilidadService();
                movilidadService.EliminarMovilidad(movilidadBean.getIdmovilidad());
                listaMovilidadBean = movilidadService.ObtenerMovilidad();
                limpiarMovilidad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String ActualizarMovilidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MovilidadService movilidadService = BeanFactory.getMovilidadService();
                movilidadService.ActualizarMovilidad(movilidadBean);
                listaMovilidadBean = movilidadService.ObtenerMovilidad();
                limpiarMovilidad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ObtenerMovilidadPorId(Object object) {
        try {
            movilidadBean = (MovilidadBean) object;
            MovilidadService movilidadService = BeanFactory.getMovilidadService();
            movilidadBean = movilidadService.ObtenerMovilidadPorId(movilidadBean.getIdmovilidad());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardar() {
        try {
            if (movilidadBean.getIdmovilidad() == null) {
                InsertarMovilidad();
            } else {
                ActualizarMovilidad();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarMovilidad() {
        try {
            movilidadBean = new MovilidadBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void rowSelectMovilidad(SelectEvent event){
        try {
            movilidadBean = (MovilidadBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public MovilidadBean getMovilidadBean() {
        if (movilidadBean == null) {
            movilidadBean = new MovilidadBean();
        }
        return movilidadBean;
    }

    public void setMovilidadBean(MovilidadBean movilidadBean) {
        this.movilidadBean = movilidadBean;
    }

    public List<MovilidadBean> getListaMovilidadBean() {
        if (listaMovilidadBean == null) {
            listaMovilidadBean = new ArrayList<>();
        }
        return listaMovilidadBean;
    }

    public void setListaMovilidadBean(List<MovilidadBean> listaMovilidadBean) {
        this.listaMovilidadBean = listaMovilidadBean;
    }

}
