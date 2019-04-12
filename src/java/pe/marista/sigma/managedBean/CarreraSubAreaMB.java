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
import pe.marista.sigma.bean.CarreraAreaBean;
import pe.marista.sigma.bean.CarreraSubAreaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CarreraAreaService;
import pe.marista.sigma.service.CarreraSubAreaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class CarreraSubAreaMB implements Serializable{

    @PostConstruct
    public void CarreraSubAreaMB() {
        
        try {
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            getCarreraAreaBean();
            listaCarreraAreaBean = carreraAreaService.obtenerCarreraArea();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    private CarreraSubAreaBean carreraSubAreaBean;
    private List<CarreraSubAreaBean> listaCarreraSubAreaBean;
    private CarreraAreaBean carreraAreaBean;
    private List<CarreraAreaBean> listaCarreraAreaBean;
    
    public void obtenerTodosSA(){
        try {
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            listaCarreraSubAreaBean = carreraSubAreaService.obtenerTodosSA();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    
    public void obtenerSubAreaPorId(Object object){
        try {
            carreraSubAreaBean = (CarreraSubAreaBean) object;
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            carreraSubAreaBean = carreraSubAreaService.obtenerSubAreaPorId(carreraSubAreaBean.getIdCarreraSubArea());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void limpiarCarreraSA(){
        carreraSubAreaBean = new CarreraSubAreaBean();
    }
    
    public String insertarSubArea(){
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                carreraSubAreaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                carreraSubAreaService.insertarSubArea(carreraSubAreaBean);
                listaCarreraSubAreaBean = carreraSubAreaService.obtenerTodosSA();
                limpiarCarreraSA();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarSubArea() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
                carreraSubAreaService.modificarSubArea(carreraSubAreaBean);
                listaCarreraSubAreaBean = carreraSubAreaService.obtenerTodosSA();
                limpiarCarreraSA();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
    
     public String eliminarCarreraSubArea() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
                carreraSubAreaService.eliminarSubArea(carreraSubAreaBean.getIdCarreraSubArea());
                listaCarreraSubAreaBean = carreraSubAreaService.obtenerTodosSA();
                limpiarCarreraSA();
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
            carreraSubAreaBean = (CarreraSubAreaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarSubArea() {
        try {
            if (carreraSubAreaBean.getIdCarreraSubArea() == null) {
                insertarSubArea();
            } else {
                modificarSubArea();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    
    public CarreraSubAreaBean getCarreraSubAreaBean() {
        if(carreraSubAreaBean == null){
            carreraSubAreaBean = new CarreraSubAreaBean();
        }
        return carreraSubAreaBean;
    }

    public void setCarreraSubAreaBean(CarreraSubAreaBean carreraSubAreaBean) {
        this.carreraSubAreaBean = carreraSubAreaBean;
    }

    public List<CarreraSubAreaBean> getListaCarreraSubAreaBean() {
        if(listaCarreraSubAreaBean == null){
            listaCarreraSubAreaBean = new ArrayList<>();
        }
        return listaCarreraSubAreaBean;
    }

    public void setListaCarreraSubAreaBean(List<CarreraSubAreaBean> listaCarreraSubAreaBean) {
        this.listaCarreraSubAreaBean = listaCarreraSubAreaBean;
    }

    public CarreraAreaBean getCarreraAreaBean() {
        if(carreraAreaBean == null){
            carreraAreaBean = new CarreraAreaBean();
        }
        return carreraAreaBean;
    }

    public void setCarreraAreaBean(CarreraAreaBean carreraAreaBean) {
        this.carreraAreaBean = carreraAreaBean;
    }

    public List<CarreraAreaBean> getListaCarreraAreaBean() {
        if(listaCarreraAreaBean == null){
            listaCarreraAreaBean = new ArrayList<>();
        }
        return listaCarreraAreaBean;
    }

    public void setListaCarreraAreaBean(List<CarreraAreaBean> listaCarreraAreaBean) {
        this.listaCarreraAreaBean = listaCarreraAreaBean;
    }
    
        
    }
    

