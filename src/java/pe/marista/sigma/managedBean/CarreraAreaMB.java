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
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CarreraAreaBean;
import pe.marista.sigma.bean.CarreraBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CarreraAreaService;
import pe.marista.sigma.service.CarreraService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class CarreraAreaMB implements Serializable {

    @PostConstruct
    public void CarreraAreaMB() {
        try {

            //            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
//            getListaGradoAcademicoBean(); 
//            GradoAcademicoService GradoAcademicoService = new GradoAcademicoService();
//            getListaGradoAcademicoBean();
//            listaGradoAcademicoBean = GradoAcademicoService.obtenerTodos();
            GradoAcademicoService  gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademicoBean();
            listaGradoAcademicoBean =  gradoAcademicoService.obtenerTodos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    private CarreraAreaBean carreraAreaBean;
    private List<CarreraBean> listaCarreraBean;
    private List<CarreraAreaBean> listaCarreraAreaBean;
    private GradoAcademicoBean gradoAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;

    public void obtenerCarreraArea() {
        try {
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            listaCarreraAreaBean = carreraAreaService.obtenerCarreraArea();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCarreraAreaPorId(Object object) {
        try {
            carreraAreaBean = (CarreraAreaBean) object;
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            carreraAreaBean = carreraAreaService.obtenerCarreraAreaPorId(carreraAreaBean.getIdCarreraArea());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCarreraArea() {
        carreraAreaBean = new CarreraAreaBean();
    }

    public String insertarCarreraArea() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                carreraAreaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                carreraAreaService.insertarCarreraArea(carreraAreaBean);
                listaCarreraAreaBean = carreraAreaService.obtenerCarreraArea();
                limpiarCarreraArea();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
    
    public String modificarCarreraArea() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
                carreraAreaService.modificarCarreraArea(carreraAreaBean);
                listaCarreraAreaBean = carreraAreaService.obtenerCarreraArea();
                limpiarCarreraArea();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarCarreraArea() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
                carreraAreaService.eliminarCarreraArea(carreraAreaBean.getIdCarreraArea());
                listaCarreraAreaBean = carreraAreaService.obtenerCarreraArea();
                limpiarCarreraArea();
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
            carreraAreaBean = (CarreraAreaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarCarrera() {
        try {
            if (carreraAreaBean.getIdCarreraArea() == null) {
                insertarCarreraArea();
            } else {
                modificarCarreraArea();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public CarreraAreaBean getCarreraAreaBean() {

        if (carreraAreaBean == null) {
            carreraAreaBean = new CarreraAreaBean();
        }
        return carreraAreaBean;
    }

    public void setCarreraAreaBean(CarreraAreaBean carreraAreaBean) {
        this.carreraAreaBean = carreraAreaBean;
    }

    public List<CarreraAreaBean> getListaCarreraAreaBean() {
        return listaCarreraAreaBean;
    }

    public void setListaCarreraAreaBean(List<CarreraAreaBean> listaCarreraAreaBean) {

        if (listaCarreraAreaBean == null) {
            listaCarreraAreaBean = new ArrayList<>();
        }
        this.listaCarreraAreaBean = listaCarreraAreaBean;
    }

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {

        if (listaGradoAcademicoBean == null) {
            listaGradoAcademicoBean = new ArrayList<>();
        }
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
    }

    public List<CarreraBean> getListaCarreraBean() {
        if (listaCarreraBean == null) {
            listaCarreraBean = new ArrayList<>();
        }
        return listaCarreraBean;
    }

    public void setListaCarreraBean(List<CarreraBean> listaCarreraBean) {
        this.listaCarreraBean = listaCarreraBean;
    }
}
