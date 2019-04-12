/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent; 
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.BecaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.BecaService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
@ManagedBean
@ViewScoped
public class BecaMB extends BaseMB implements Serializable{

    
    public BecaMB() {
        try {
            BecaService becaService = BeanFactory.getBecaService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaBeca();
            listaCodigo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_BECA));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    private BecaBean becaBean;
    private List<BecaBean> listaBeca;
    private List<CodigoBean> listaCodigo;

    public List<CodigoBean> getListaCodigo() {
        return listaCodigo;
    }

    public void setListaCodigo(List<CodigoBean> listaCodigo) {
        this.listaCodigo = listaCodigo;
    }

    public BecaBean getBecaBean() {
        return becaBean;
    }
    
    public void setBecaBean(BecaBean becaBean) {
        this.becaBean = becaBean;
    }

    public List<BecaBean> getListaBeca() {
        return listaBeca;
    }

    public void setListaBeca(List<BecaBean> listaBeca) {
        this.listaBeca = listaBeca;
    }
    public void obtenerTodos() {
        try {
            BecaService becaService = BeanFactory.getBecaService();
            listaBeca = becaService.obtenerTodos();
            becaBean = new BecaBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarBeca() {
       becaBean = new BecaBean();
    }

    public void obtenerPorId(BecaBean becaBean) {
        try {
            BecaService becaService = BeanFactory.getBecaService();
            becaBean = becaService.buscarPorId(becaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarBeca() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                becaBean.setCreaPor(usuarioBean.getUsuario());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                becaBean.setCreaFecha(formato.parse(date));
                BecaService becaService = BeanFactory.getBecaService();
                becaService.insertar(becaBean);
                listaBeca = becaService.obtenerTodos();
                limpiarBeca();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarBeca() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                BecaService becaService = BeanFactory.getBecaService();
                becaService.actualizar(becaBean);
                listaBeca = becaService.obtenerTodos();
                limpiarBeca();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarBeca() {
        try {
            if (becaBean.getIdBeca() == null) {
                insertarBeca();
            } 
            else {
                modificarBeca();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public String eliminarBeca() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                BecaService becaService = BeanFactory.getBecaService();
                becaService.eliminar(becaBean);
                listaBeca = becaService.obtenerTodos();
                limpiarBeca();
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
                BecaService becaService = BeanFactory.getBecaService();
                becaService.cambiarEstado(becaBean);
                listaBeca = becaService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelect(SelectEvent event) {
        try {
            becaBean = new BecaBean();
            becaBean = (BecaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
     public void ponerBeca(Object beca) {
        try {
            becaBean = (BecaBean) beca;
        } catch (Exception err) {

            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }


}
