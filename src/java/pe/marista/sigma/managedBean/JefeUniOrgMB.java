/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

//import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.JefeUniOrgBean;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.JefeUniOrgService;
import pe.marista.sigma.service.UniNegUniOrgService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS001
 */
public class JefeUniOrgMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of JefeUniOrgMB
     */
    @PostConstruct
    public void JefeUniOrgMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            estado = Boolean.TRUE;
//            JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
//            getListaJefeUniOrgBean();
//            listaJefeUniOrgPorUniOrgBean = jefeUniOrgService.obtenerJefeUniOrgPorUniNegPorEstado(estado, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            getListaUnidadOrganicaPorUniNeg();
            listaUnidadOrganicaPorUniNeg = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private JefeUniOrgBean jefeUniOrgBean;
    private List<UniNegUniOrgBean> listaUnidadOrganicaPorUniNeg;
//    private List<JefeUniOrgBean> listaJefeUniOrgBean;
    private List<JefeUniOrgBean> listaJefeUniOrgPorUniOrgBean;
    private UsuarioBean beanUsuarioSesion;
    private Boolean estado;
    private Integer idUniOrg;
    public void limpiarJefeUniOrg() {
        jefeUniOrgBean = new JefeUniOrgBean();
    }
    
    public void rowSelect(SelectEvent event) {
        try {
            
            jefeUniOrgBean = (JefeUniOrgBean) event.getObject();
//            JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService(); 
//            jefeUniOrgBean = jefeUniOrgService.obtenerJefeUniOrgPorId(jefeUniOrgBean);
 } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    public void obtenerUniOrgPorId(Integer idUniO) {
        try {
            limpiarJefeUniOrg();
            idUniOrg=idUniO;
            JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
            UnidadOrganicaService unidadOrganicaService=BeanFactory.getUnidadOrganicaService();
            UnidadOrganicaBean unidadOrganica = new UnidadOrganicaBean();
            unidadOrganica.setIdUniOrg(idUniOrg);
            unidadOrganica=unidadOrganicaService.obtenerUnidadOrganicaPorId(unidadOrganica);
            jefeUniOrgBean.setUnidadOrganicaBean(unidadOrganica);
            listaJefeUniOrgPorUniOrgBean = jefeUniOrgService.obtenerJefeUniOrgPorUniOrg(idUniOrg, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerJefePorId(Object objeto) {
        try {
            jefeUniOrgBean = (JefeUniOrgBean) objeto;
            JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
            jefeUniOrgBean = jefeUniOrgService.obtenerJefeUniOrgPorId(jefeUniOrgBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String eliminarJefe() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
                jefeUniOrgService.eliminarJefeUniOrg(jefeUniOrgBean);
                obtenerUniOrgPorId(jefeUniOrgBean.getUnidadOrganicaBean().getIdUniOrg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarJefeUniOrg() {
        if (jefeUniOrgBean.getIdJefeUniOrg() == null) {
            insertarJefeUniOrg();
        } else {
            modificarJefeUniOrg();
        }
    }

    public String insertarJefeUniOrg() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
                UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
                jefeUniOrgBean.setCreaPor(beanUsuarioSesion.getUsuario());
                jefeUniOrgBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                jefeUniOrgService.insertarJefeUniOrg(jefeUniOrgBean, listaJefeUniOrgPorUniOrgBean);

                getListaUnidadOrganicaPorUniNeg();
                listaUnidadOrganicaPorUniNeg = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                obtenerUniOrgPorId(idUniOrg);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarJefeUniOrg() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                JefeUniOrgService jefeUniOrgService = BeanFactory.getJefeUniOrgService();
                UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
                jefeUniOrgBean.setModiPor(beanUsuarioSesion.getUsuario());
                jefeUniOrgBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                jefeUniOrgService.modificarJefeUniOrg(jefeUniOrgBean);

                getListaUnidadOrganicaPorUniNeg();
                listaUnidadOrganicaPorUniNeg = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                obtenerUniOrgPorId(jefeUniOrgBean.getUnidadOrganicaBean().getIdUniOrg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //metodos getter and setter
    public JefeUniOrgBean getJefeUniOrgBean() {
        if (jefeUniOrgBean == null) {
            jefeUniOrgBean = new JefeUniOrgBean();
        }
        return jefeUniOrgBean;
    }

    public void setJefeUniOrgBean(JefeUniOrgBean jefeUniOrgBean) {
        this.jefeUniOrgBean = jefeUniOrgBean;
    }

//    public List<JefeUniOrgBean> getListaJefeUniOrgBean() {
//        if (listaJefeUniOrgBean == null) {
//            listaJefeUniOrgBean = new ArrayList<>();
//        }
//        return listaJefeUniOrgBean;
//    }
//
//    public void setListaJefeUniOrgBean(List<JefeUniOrgBean> listaJefeUniOrgBean) {
//        this.listaJefeUniOrgBean = listaJefeUniOrgBean;
//    }
    public List<JefeUniOrgBean> getListaJefeUniOrgPorUniOrgBean() {
        if (listaJefeUniOrgPorUniOrgBean == null) {
            listaJefeUniOrgPorUniOrgBean = new ArrayList<>();
        }
        return listaJefeUniOrgPorUniOrgBean;
    }

    public void setListaJefeUniOrgPorUniOrgBean(List<JefeUniOrgBean> listaJefeUniOrgPorUniOrgBean) {
        this.listaJefeUniOrgPorUniOrgBean = listaJefeUniOrgPorUniOrgBean;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<UniNegUniOrgBean> getListaUnidadOrganicaPorUniNeg() {
        if (listaUnidadOrganicaPorUniNeg == null) {
            listaUnidadOrganicaPorUniNeg = new ArrayList<>();
        }
        return listaUnidadOrganicaPorUniNeg;
    }

    public void setListaUnidadOrganicaPorUniNeg(List<UniNegUniOrgBean> listaUnidadOrganicaPorUniNeg) {
        this.listaUnidadOrganicaPorUniNeg = listaUnidadOrganicaPorUniNeg;
    }

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

}
