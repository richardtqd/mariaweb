package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.AreaEstudioBean;
import pe.marista.sigma.bean.CarreraAreaBean;
import pe.marista.sigma.bean.CarreraBean;
import pe.marista.sigma.bean.CarreraSubAreaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.UsuarioBean;

import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CarreraAreaService;
import pe.marista.sigma.service.CarreraService;
import pe.marista.sigma.service.CarreraSubAreaService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class CarreraMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CarreraMB
     */
    @PostConstruct
    public void CarreraMB() {
        try {

            obtenerTodosCAR();
            obtenerTodosSubArea();
            obtenerTodosCarreraArea();
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademicoBean();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodos();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private CarreraBean carreraBean;
    private List<CarreraBean> listaCarreraBean;

    private List<CarreraSubAreaBean> listaCarreraSubAreaBean;
    private CarreraSubAreaBean carreraSubAreaBean;

    private CarreraAreaBean carreraAreaBean;
    private List<CarreraAreaBean> listaCarreraAreaBean;
    private List<GradoAcademicoBean> listaCarreraAreaPorGAcademico;
    private List<CarreraAreaBean> listaGradoAcademicoBeanFiltro;

    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    
    private List<CarreraBean> listaCarreraFiltroBean;
    private List<CarreraAreaBean> listaCarreraAreaFiltroBean;
    private List<CarreraSubAreaBean> listaCarreraSubAreaBeanFiltro;

    private GradoAcademicoBean gradoAcademicoBean;
    
    private Integer idCarreraArea;
    private Integer idCarreraSubArea;
    private Integer idCarrera;
    private Integer idGradoAcademico;

    //Variables Locales ID
    public Integer getIdCarreraArea() {
        return idCarreraArea;
    }

    public void setIdCarreraArea(Integer idCarreraArea) {
        this.idCarreraArea = idCarreraArea;
    }

    public Integer getIdCarreraSubArea() {
        return idCarreraSubArea;
    }

    public void setIdCarreraSubArea(Integer idCarreraSubArea) {
        this.idCarreraSubArea = idCarreraSubArea;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    //-------------------------- INICIO CARRERA -------------------------------
    public void obtenerTodosCAR() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaCarreraBean = carreraService.obtenerTodosCAR();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCarreraPorId(Object object) {
        try {
            carreraBean = (CarreraBean) object;
            CarreraService carreraService = BeanFactory.getCarreraService();
            carreraBean = carreraService.obtenerCarreraPorId(carreraBean.getIdCarrera());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCarrera() {
        carreraBean = new CarreraBean();
    }

    public String insertarCarrera() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraService carreraService = BeanFactory.getCarreraService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                carreraBean.setCreaPor(beanUsuarioSesion.getUsuario());
                carreraService.insertarCarrera(carreraBean);
                listaCarreraBean = carreraService.obtenerTodosCAR();
                limpiarCarrera();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarCarrera() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraService carreraService = BeanFactory.getCarreraService();
                carreraService.modificarCarrera(carreraBean);
                listaCarreraBean = carreraService.obtenerTodosCAR();
                limpiarCarrera();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarCarrera() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraService carreraService = BeanFactory.getCarreraService();
                carreraService.eliminarCarrera(carreraBean.getIdCarrera());
                listaCarreraBean = carreraService.obtenerTodosCAR();
                limpiarCarrera();
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
            carreraBean = (CarreraBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarCarrera() {
        try {
            if (carreraBean.getIdCarrera() == null) {
                insertarCarrera();
            } else {
                modificarCarrera();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //-------------------------- FIN CARRERA -------------------------------

    //--------------------------  INICO SUB AREA -------------------------------
    public void obtenerTodosSubArea() {
        try {
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            listaCarreraSubAreaBean = carreraSubAreaService.obtenerTodosSA();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCarreraSubPorId(Object object) {
        try {
            carreraSubAreaBean = (CarreraSubAreaBean) object;
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            carreraSubAreaBean = carreraSubAreaService.obtenerSubAreaPorId(carreraSubAreaBean.getIdCarreraSubArea());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCarreraSubArea() {
        carreraSubAreaBean = new CarreraSubAreaBean();
    }

    public String insertarCarreraSubArea() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                carreraSubAreaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                carreraSubAreaService.insertarSubArea(carreraSubAreaBean);
                listaCarreraSubAreaBean = carreraSubAreaService.obtenerTodosSA();
                limpiarCarreraSubArea();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarCarreraSubArea() {
//         modificarCarreraSubArea
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
                carreraSubAreaService.modificarSubArea(carreraSubAreaBean);
                listaCarreraSubAreaBean = carreraSubAreaService.obtenerTodosSA();
                limpiarCarreraSubArea();
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
                limpiarCarreraSubArea();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void RowSelectCarreraSubArea(SelectEvent event) {
        try {
            carreraSubAreaBean = (CarreraSubAreaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarCarreraSubArea() {
        try {
            if (carreraSubAreaBean.getIdCarreraSubArea() == null) {
                insertarCarreraSubArea();
            } else {
                modificarCarreraSubArea();//Cambios
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Metodos Auxiliares
    public void obtenerCarreraSubAreaPorCarreraArea() {
        try {
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            listaCarreraSubAreaBean = carreraSubAreaService.obtenerCarreraSubAreaPorCarreraArea(this.idCarreraArea);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    //Auxiliares de Carrera Sub Area
    public void obtenerCarreraAreaPorGradoAcademico() {
        try {
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            listaCarreraAreaBean = carreraAreaService.obtenerCarreraAreaPorGradoAcademico(idGradoAcademico);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    //Auxiliares de Carrera
    public void obtenerCarreraPorCarreraSubArea() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaCarreraBean = carreraService.obtenerCarreraPorCarreraSubArea(idCarreraSubArea);
            if(listaCarreraBean == null){
                listaCarreraBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    public void obtenerCarreraPorCarreraArea(){
        
    }
//  listaCarreraFiltroBean;
//  listaCarreraAreaFiltroBean;
//  listaCarreraSubAreaBeanFiltro;

    //--------------------------  FIN SUB AREA -------------------------------
    //--------------------------  INICO AREA -------------------------------
    public void obtenerTodosCarreraArea() {
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

    public void RowSelectCarreraArea(SelectEvent event) {
        try {
            carreraAreaBean = (CarreraAreaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarCarreraArea() {
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
    //--------------------------  FIN AREA -------------------------------

    //METODOS
    public CarreraBean getCarreraBean() {
        if (carreraBean == null) {
            carreraBean = new CarreraBean();
        }
        return carreraBean;
    }

    public void setCarreraBean(CarreraBean carreraBean) {
        this.carreraBean = carreraBean;
    }

    public List<CarreraBean> getListaCarreraBean() {
       
        return listaCarreraBean;
    }

    public void setListaCarreraBean(List<CarreraBean> listaCarreraBean) {
        this.listaCarreraBean = listaCarreraBean;
    }

    public List<CarreraSubAreaBean> getListaCarreraSubAreaBean() {
        return listaCarreraSubAreaBean;
    }

    public void setListaCarreraSubAreaBean(List<CarreraSubAreaBean> listaCarreraSubAreaBean) {
        this.listaCarreraSubAreaBean = listaCarreraSubAreaBean;
    }

    public CarreraSubAreaBean getCarreraSubAreaBean() {
        if (carreraSubAreaBean == null) {
            carreraSubAreaBean = new CarreraSubAreaBean();
        }
        return carreraSubAreaBean;
    }

    public void setCarreraSubAreaBean(CarreraSubAreaBean carreraSubAreaBean) {
        this.carreraSubAreaBean = carreraSubAreaBean;
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
        if(listaCarreraAreaBean == null){
            listaCarreraAreaBean = new ArrayList<>();
        }
        return listaCarreraAreaBean;
    }

    public void setListaCarreraAreaBean(List<CarreraAreaBean> listaCarreraAreaBean) {
        this.listaCarreraAreaBean = listaCarreraAreaBean;
    }

    public List<GradoAcademicoBean> getListaCarreraAreaPorGAcademico() {
        if (listaCarreraAreaPorGAcademico == null) {
            listaCarreraAreaPorGAcademico = new ArrayList<>();
        }
        return listaCarreraAreaPorGAcademico;
    }

    public void setListaCarreraAreaPorGAcademico(List<GradoAcademicoBean> listaCarreraAreaPorGAcademico) {
        this.listaCarreraAreaPorGAcademico = listaCarreraAreaPorGAcademico;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
    }

    public List<CarreraAreaBean> getListaGradoAcademicoBeanFiltro() {
        return listaGradoAcademicoBeanFiltro;
    }

    public void setListaGradoAcademicoBeanFiltro(List<CarreraAreaBean> listaGradoAcademicoBeanFiltro) {
        this.listaGradoAcademicoBeanFiltro = listaGradoAcademicoBeanFiltro;
    }

    public List<CarreraBean> getListaCarreraFiltroBean() {
        if(listaCarreraFiltroBean == null){
            listaCarreraFiltroBean = new ArrayList<>();
        }
        return listaCarreraFiltroBean;
    }

    public void setListaCarreraFiltroBean(List<CarreraBean> listaCarreraFiltroBean) {
        this.listaCarreraFiltroBean = listaCarreraFiltroBean;
    }

    public List<CarreraAreaBean> getListaCarreraAreaFiltroBean() {
        if(listaCarreraAreaFiltroBean == null){
            listaCarreraAreaFiltroBean = new ArrayList<>();
        }
        return listaCarreraAreaFiltroBean;
    }

    public void setListaCarreraAreaFiltroBean(List<CarreraAreaBean> listaCarreraAreaFiltroBean) {
        this.listaCarreraAreaFiltroBean = listaCarreraAreaFiltroBean;
    }

    public List<CarreraSubAreaBean> getListaCarreraSubAreaBeanFiltro() {
        if(listaCarreraSubAreaBeanFiltro == null){
            listaCarreraSubAreaBeanFiltro = new ArrayList<>();
        }
        return listaCarreraSubAreaBeanFiltro;
    }

    public void setListaCarreraSubAreaBeanFiltro(List<CarreraSubAreaBean> listaCarreraSubAreaBeanFiltro) {
        this.listaCarreraSubAreaBeanFiltro = listaCarreraSubAreaBeanFiltro;
    }
//
//    public GradoAcademicoBean getGradoAcademicoBean() {
//        if(== null){
//             = new ArrayList<>();
//        }
//        return GradoAcademicoBean;
//    }
//
//    public void setGradoAcademicoBean(GradoAcademicoBean GradoAcademicoBean) {
//        this.GradoAcademicoBean = GradoAcademicoBean;
//    }

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }
        
}
