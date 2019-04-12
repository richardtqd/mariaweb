/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.PaisService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS001
 */
public class PaisMB implements Serializable{

    public PaisMB() {
        
    }
    
    private PaisBean paisBean;
    private List<PaisBean> listaPaisBean;
    
    public void obtenerPais() {
        try {
            PaisService paisService = BeanFactory.getPaisService();
            listaPaisBean = paisService.obtenerPais();
//            PaisBean paisBean = new PaisBean();
            paisBean = new PaisBean();
            paisBean.setIdPais(null);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void obtenerPaisPorId(Object object) {
        try {
            paisBean = (PaisBean) object;
            PaisService paisService = BeanFactory.getPaisService();
            paisBean = paisService.obtenerPaisPorId(paisBean.getIdPais());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void limpiarPais() {
        paisBean = new PaisBean();
    }
    
    
    public String insertarPais() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PaisService paisService = BeanFactory.getPaisService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                paisBean.setCreaPor(beanUsuarioSesion.getUsuario());
                paisService.insertarPais(paisBean);
                listaPaisBean = paisService.obtenerPais();
                limpiarPais();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
    
    public String modificarPais() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PaisService paisService = BeanFactory.getPaisService();
                paisService.modificarPais(paisBean);
                listaPaisBean = paisService.obtenerPais();
                limpiarPais();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
    
     public String eliminarPais() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PaisService paisService = BeanFactory.getPaisService();
                paisService.eliminarPais(paisBean.getIdPais());
               listaPaisBean = paisService.obtenerPais();
                limpiarPais();
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
            paisBean = (PaisBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
     public void guardarPais() {

        try {
            System.out.println("id: "+paisBean.getIdPais()); 
            if (paisBean.getIdPais() == null) {
                insertarPais();
            } else {
                modificarPais();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public PaisBean getPaisBean() {
        if (paisBean == null)
        {
            paisBean= new PaisBean();
        }
        return paisBean;
    }

    public void setPaisBean(PaisBean paisBean) {
        this.paisBean = paisBean;
    }

    public List<PaisBean> getListaPaisBean() {
        return listaPaisBean;
    }

    public void setListaPaisBean(List<PaisBean> listaPaisBean) {
        this.listaPaisBean = listaPaisBean;
    }
    
}
