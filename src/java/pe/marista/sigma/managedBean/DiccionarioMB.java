package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.DiccionarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.DiccionarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class DiccionarioMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DiccionarioService
     */
    @PostConstruct
    public void DiccionarioMB() {
        try {
            
            DiccionarioService diccionarioService = BeanFactory.getDiccionarioService();
            getListaDiccionarioBean();
            listaDiccionarioBean = diccionarioService.obtenerDiccionario();
            
            getListaTablaDiccionarioBean();
            listaTablaDiccionarioBean = diccionarioService.obtenerTablaDiccionario();

            getListaColumnaDiccionarioBean();
            listaColumnaDiccionarioBean = diccionarioService.obtenerColumnaDiccionario();

            getListaTipoDiccionarioBean();
            listaTipoDiccionarioBean = diccionarioService.obtenerTipoDiccionario();
            
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Varialbes y Propiedades
    private DiccionarioBean diccionarioBean;
    private List<DiccionarioBean> listaDiccionarioBean;
    private List<DiccionarioBean> listaTablaDiccionarioBean;
    private List<DiccionarioBean> listaColumnaDiccionarioBean;
    private List<DiccionarioBean> listaTipoDiccionarioBean;
    
    //Metodos de Logica de Negocio
  

    public String modificarDiccionario() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DiccionarioService diccionarioService = BeanFactory.getDiccionarioService();
                diccionarioService.modificarDiccionarioDescripcion(diccionarioBean);
                listaDiccionarioBean = diccionarioService.obtenerDiccionario();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }
    public String ejecutarStoredProcedure(Object object) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                diccionarioBean = (DiccionarioBean) object;
                DiccionarioService diccionarioService = BeanFactory.getDiccionarioService();
                diccionarioService.ejecutarStoredProcedure(diccionarioBean);
                listaDiccionarioBean = diccionarioService.obtenerDiccionario();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }
    
   
    public void rowSelect(SelectEvent event) {
        try {
            diccionarioBean = (DiccionarioBean) event.getObject();
         
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowEdit(RowEditEvent event) { 
        try {
            DiccionarioService diccionarioService = BeanFactory.getDiccionarioService();
            diccionarioBean = new DiccionarioBean();
            diccionarioBean.setDescripcion(((DiccionarioBean) event.getObject()).getDescripcion());
            diccionarioBean.setIdObjeto(((DiccionarioBean) event.getObject()).getIdObjeto());
            diccionarioBean.setTabla(((DiccionarioBean) event.getObject()).getTabla());
            diccionarioBean.setColumna(((DiccionarioBean) event.getObject()).getColumna());
            diccionarioService.modificarDiccionarioDescripcion(diccionarioBean);
            listaDiccionarioBean = diccionarioService.obtenerDiccionario();
            FacesMessage msg = new FacesMessage("Tabla Editada:", ((DiccionarioBean) event.getObject()).getTabla());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            diccionarioBean = new DiccionarioBean();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", ((DiccionarioBean) event.getObject()).getTabla());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void obtenerDicId(Object object) {
        try {
            diccionarioBean = (DiccionarioBean) object;
            DiccionarioService diccionarioService = BeanFactory.getDiccionarioService();
            diccionarioBean = diccionarioService.obtenerPorId(diccionarioBean);
            
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    

    //Getter y Setter
    public DiccionarioBean getDiccionarioBean() {
        if (diccionarioBean == null) {
            diccionarioBean = new DiccionarioBean();
        }
        return diccionarioBean;
    }

    public void setDiccionarioBean(DiccionarioBean diccionarioBean) {
        this.diccionarioBean = diccionarioBean;
    }

    public List<DiccionarioBean> getListaDiccionarioBean() {
        if (listaDiccionarioBean == null) {
            listaDiccionarioBean = new ArrayList<>();
        }
        return listaDiccionarioBean;
    }

    public void setListaDiccionarioBean(List<DiccionarioBean> listaDiccionarioBean) {
        this.listaDiccionarioBean = listaDiccionarioBean;
    }

    public List<DiccionarioBean> getListaTablaDiccionarioBean() {
        if (listaTablaDiccionarioBean == null) {
            listaTablaDiccionarioBean = new ArrayList<>();
        }
        return listaTablaDiccionarioBean;
    }

    public void setListaTablaDiccionarioBean(List<DiccionarioBean> listaTablaDiccionarioBean) {
        this.listaTablaDiccionarioBean = listaTablaDiccionarioBean;
    }

    public List<DiccionarioBean> getListaColumnaDiccionarioBean() {
        if (listaColumnaDiccionarioBean == null) {
            listaColumnaDiccionarioBean = new ArrayList<>();
        }
        return listaColumnaDiccionarioBean;
    }

    public void setListaColumnaDiccionarioBean(List<DiccionarioBean> listaColumnaDiccionarioBean) {
        this.listaColumnaDiccionarioBean = listaColumnaDiccionarioBean;
    }

    public List<DiccionarioBean> getListaTipoDiccionarioBean() {
        if (listaTipoDiccionarioBean == null) {
            listaTipoDiccionarioBean = new ArrayList<>();
        }
        return listaTipoDiccionarioBean;
    }

    public void setListaTipoDiccionarioBean(List<DiccionarioBean> listaTipoDiccionarioBean) {
        this.listaTipoDiccionarioBean = listaTipoDiccionarioBean;
    }

}
