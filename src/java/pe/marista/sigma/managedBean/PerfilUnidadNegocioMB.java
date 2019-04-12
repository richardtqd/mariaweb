/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilUnidadNegocioBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.PerfilUnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public  class PerfilUnidadNegocioMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of PerfilUnidadNegocioMB
     */
    public PerfilUnidadNegocioMB() {
        try {
            cargarLista();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }
    private List<String> listaUnidadNegocioOrig = new ArrayList<>();
    private List<String> listaUnidadNegocioDest = new ArrayList<>();
    private List<PerfilBean> listaPerfilBean;
    private List<UnidadNegocioBean> listaUnidadNegocioBean;
    private DualListModel<String> dualListModelUnidadNegocio;
    private PerfilUnidadNegocioBean perfilUnidadNegocioBean;
    private PerfilBean perfilBean;
    private PerfilUnidadNegocioBean perfilUnidadNegocioFiltroBean;

    //Metodos
    public void limpiarBean() {
        perfilBean = new PerfilBean();
        cargarLista();
//        dualListModelUnidadNegocio = new DualListModel<>(listaUnidadNegocioOrig, listaUnidadNegocioDest);
    }

    public final void cargarLista() {
        try {
            listaUnidadNegocioOrig = new ArrayList<>();
            listaUnidadNegocioDest = new ArrayList<>();
            PerfilUnidadNegocioService perfilUnidadNegocioService = BeanFactory.getPerfilUnidadNegocioService();
            listaUnidadNegocioBean = perfilUnidadNegocioService.obtenerTodosUnidadNegocio();
            for (UnidadNegocioBean unidadNegocioBean : listaUnidadNegocioBean) {
                listaUnidadNegocioOrig.add(unidadNegocioBean.getNombreUniNeg());
            }
            dualListModelUnidadNegocio = new DualListModel<>(listaUnidadNegocioOrig, listaUnidadNegocioDest);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltro() {
        try {
            if (perfilUnidadNegocioFiltroBean.getPerfilBean().getNombre() != null) {
                perfilUnidadNegocioFiltroBean.getPerfilBean().setNombre(perfilUnidadNegocioFiltroBean.getPerfilBean().getNombre().toUpperCase().trim());
            }
            PerfilUnidadNegocioService perfilUsuarioService = BeanFactory.getPerfilUnidadNegocioService();
            listaPerfilBean = perfilUsuarioService.obtenerTodosFiltroPerfil(perfilUnidadNegocioFiltroBean.getPerfilBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object perfil) {
        try {
            
            PerfilUnidadNegocioService perfilUnidadNegocioService = BeanFactory.getPerfilUnidadNegocioService();
            perfilUnidadNegocioBean.setPerfilBean((PerfilBean) perfil);
//            perfilUnidadNegocioBean = perfilUnidadNegocioService.obtenerPorId(perfilUnidadNegocioBean);
            List<PerfilUnidadNegocioBean> lista = perfilUnidadNegocioService.obtenerPorIdPerfil(perfilUnidadNegocioBean);
            perfilBean = new PerfilBean();
//            listaUnidadNegocioDest = new ArrayList<>();
//            listaUnidadNegocioDest = new ArrayList<>();
            cargarLista();
            for (int i = 0; i < lista.size(); i++) {
                listaUnidadNegocioDest.add(lista.get(i).getNombreUniNeg());
                listaUnidadNegocioOrig.remove(lista.get(i).getNombreUniNeg());
            }
            dualListModelUnidadNegocio = new DualListModel<>(listaUnidadNegocioOrig, listaUnidadNegocioDest);
            setPerfilBean(perfilUnidadNegocioBean.getPerfilBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPerfil() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PerfilUnidadNegocioService perfilUnidadNegocioService = BeanFactory.getPerfilUnidadNegocioService();
                perfilUnidadNegocioService.insertar(perfilBean, dualListModelUnidadNegocio.getTarget(), listaUnidadNegocioBean);
                listaPerfilBean = perfilUnidadNegocioService.obtenerTodosPerfil();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPerfil() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PerfilUnidadNegocioService perfilUnidadNegocioService = BeanFactory.getPerfilUnidadNegocioService();
                perfilUnidadNegocioService.actualizar(perfilBean, dualListModelUnidadNegocio.getTarget(), listaUnidadNegocioBean);
                listaPerfilBean = perfilUnidadNegocioService.obtenerTodosPerfil();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPerfil() {
        if (perfilBean.getIdPerfil() == null) {
            insertarPerfil();
        } else {
            modificarPerfil();
        }
    }

    public String eliminarPerfil() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PerfilUnidadNegocioService perfilUnidadNegocioService = BeanFactory.getPerfilUnidadNegocioService();
                perfilUnidadNegocioService.eliminarTodos(perfilUnidadNegocioBean);
                listaPerfilBean = perfilUnidadNegocioService.obtenerTodosPerfil();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //Metodos Getters y Setters
    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
        if (listaUnidadNegocioBean == null) {
            listaUnidadNegocioBean = new ArrayList<>();
        }
        return listaUnidadNegocioBean;
    }

    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
    }

    public List<PerfilBean> getListaPerfilBean() {
        if (listaPerfilBean == null) {
            listaPerfilBean = new ArrayList<>();
        }
        return listaPerfilBean;
    }

    public void setListaPerfilBean(List<PerfilBean> listaPerfilBean) {
        this.listaPerfilBean = listaPerfilBean;
    }

    public DualListModel<String> getDualListModelUnidadNegocio() {
        return dualListModelUnidadNegocio;
    }

    public void setDualListModelUnidadNegocio(DualListModel<String> dualListModelUnidadNegocio) {
        this.dualListModelUnidadNegocio = dualListModelUnidadNegocio;
    }

    public List<String> getListaUnidadNegocioOrig() {
        if (listaUnidadNegocioOrig == null) {
            listaUnidadNegocioOrig = new ArrayList<>();
        }
        return listaUnidadNegocioOrig;
    }

    public void setListaUnidadNegocioOrig(List<String> listaUnidadNegocioOrig) {
        this.listaUnidadNegocioOrig = listaUnidadNegocioOrig;
    }

    public List<String> getListaUnidadNegocioDest() {
        if (listaUnidadNegocioDest == null) {
            listaUnidadNegocioDest = new ArrayList<>();
        }
        return listaUnidadNegocioDest;
    }

    public void setListaUnidadNegocioDest(List<String> listaUnidadNegocioDest) {
        this.listaUnidadNegocioDest = listaUnidadNegocioDest;
    }

    public PerfilUnidadNegocioBean getPerfilUnidadNegocioBean() {
        if (perfilUnidadNegocioBean == null) {
            perfilUnidadNegocioBean = new PerfilUnidadNegocioBean();
        }
        return perfilUnidadNegocioBean;
    }

    public void setPerfilUnidadNegocioBean(PerfilUnidadNegocioBean perfilUnidadNegocioBean) {
        this.perfilUnidadNegocioBean = perfilUnidadNegocioBean;
    }

    public PerfilUnidadNegocioBean getPerfilUnidadNegocioFiltroBean() {
        if (perfilUnidadNegocioFiltroBean == null) {
            perfilUnidadNegocioFiltroBean = new PerfilUnidadNegocioBean();
        }
        return perfilUnidadNegocioFiltroBean;
    }

    public void setPerfilUnidadNegocioFiltroBean(PerfilUnidadNegocioBean perfilUnidadNegocioFiltroBean) {
        this.perfilUnidadNegocioFiltroBean = perfilUnidadNegocioFiltroBean;
    }

    public PerfilBean getPerfilBean() {
        if (perfilBean == null) {
            perfilBean = new PerfilBean();
        }
        return perfilBean;
    }

    public void setPerfilBean(PerfilBean perfilBean) {
        this.perfilBean = perfilBean;
    }
}
