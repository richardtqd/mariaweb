/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.TipoCambioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
@ManagedBean
@ViewScoped
public class TipoCambioMB extends BaseMB implements Serializable{

    /**
     * Creates a new instance of TipoCambioMB
     */
    public TipoCambioMB() {
        try{
            listaEstado = new LinkedHashMap<>();
            listaEstado.put(MaristaConstantes.ESTADO_ACTIVO_DES, MaristaConstantes.ESTADO_ACTIVO);
            listaEstado.put(MaristaConstantes.ESTADO_INACTIVO_DES, MaristaConstantes.ESTADO_INACTIVO);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
 
    private List<TipoCambioBean> listaTipoCambioBean;
    private TipoCambioBean tipoCambioBean;
    private Map<String, Integer> listaEstado;

    public List<TipoCambioBean> getListaTipoCambioBean() {
        return listaTipoCambioBean;
    }

    public void setListaTipoCambioBean(List<TipoCambioBean> listaTipoCambioBean) {
        this.listaTipoCambioBean = listaTipoCambioBean;
    }

    public TipoCambioBean getTipoCambioBean() {
        if(tipoCambioBean == null)
        {
            tipoCambioBean = new TipoCambioBean();
        }
        return tipoCambioBean;
    }

    public void setTipoCambioBean(TipoCambioBean tipoCambioBean) {
        this.tipoCambioBean = tipoCambioBean;
    }

    public Map<String, Integer> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(Map<String, Integer> listaEstado) {
        this.listaEstado = listaEstado;
    }
    
    public void obtenerTodos() {
        try {            
            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            listaTipoCambioBean = tipoCambioService.obtenerTodos();
            tipoCambioBean = new TipoCambioBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarTipoCambio() {
        tipoCambioBean = new TipoCambioBean();
    }


    public void obtenerPorId(Integer idTipoMoneda) {
        try {
            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            tipoCambioBean.setIdTipoMoneda(idTipoMoneda);
            tipoCambioBean = tipoCambioService.buscarPorId(tipoCambioBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarTipoCambio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                tipoCambioBean.setCreaPor(usuarioBean.getUsuario());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                tipoCambioBean.setCreaFecha(formato.parse(date));
                TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
                tipoCambioService.insertar(tipoCambioBean);
                listaTipoCambioBean = tipoCambioService.obtenerTodos();
                limpiarTipoCambio();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarTipoCambio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
                tipoCambioService.actualizar(tipoCambioBean);
                listaTipoCambioBean = tipoCambioService.obtenerTodos();
                limpiarTipoCambio();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarTipoCambio() {
        try {
            if (tipoCambioBean.getIdTipoMoneda() == null) {
            insertarTipoCambio();
        } else {
            modificarTipoCambio();
        }            
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        
    }

    public String eliminarTipoCambio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
                tipoCambioService.eliminar(tipoCambioBean);
                listaTipoCambioBean = tipoCambioService.obtenerTodos();
                limpiarTipoCambio();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoTipoCambio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
                tipoCambioService.cambiarEstado(tipoCambioBean);
                listaTipoCambioBean = tipoCambioService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerTipoCambio(Object tipoCambio) {
//        String pagina = null;
        try {
            tipoCambioBean = (TipoCambioBean) tipoCambio;
        } catch (Exception err) {
//            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            tipoCambioBean = (TipoCambioBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
}
