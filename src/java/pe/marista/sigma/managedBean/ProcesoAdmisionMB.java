package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ProcesoAdmisionBean;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class ProcesoAdmisionMB extends BaseMB implements Serializable {
//
//    /**
//     * Creates a new instance of ProcesoAdmisionService
//     */
//    public ProcesoAdmisionMB() {
//        cargarAnio();
//    }
//    //Varialbes y Propiedades
//    private ProcesoAdmisionBean procesoAdmisionBean;
//    private List<ProcesoAdmisionBean> listaProcesoAdmisionBean;
//    private List<Integer> listaAnios;
//
//    //Metodos de Logica de Negocio
//    public void limpiarProcesoAdmision() {
//        procesoAdmisionBean = new ProcesoAdmisionBean();
//    }
//    public final void cargarAnio() {
//        try {
//            int fin = 2100;
//            int inicio = 1990;
//            listaAnios = new ArrayList<>();
//            for (int i = inicio; i <= fin; i++) {
//                listaAnios.add(i);
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public void obtenerProcesoAdmision() {
//        try {
////            ProcesoAdmisionService procesoAdmisionService = BeanFactory.getProcesoAdmisionService();
////            listaProcesoAdmisionBean = procesoAdmisionService.obtenerProcesoAdmision();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public String insertarProcesoAdm() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
////                ProcesoAdmisionService procesoAdmisionService = BeanFactory.getProcesoAdmisionService();
////                procesoAdmisionService.insertarProcesoAdm(procesoAdmisionBean);
////                listaProcesoAdmisionBean = procesoAdmisionService.obtenerProcesoAdmision();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                limpiarProcesoAdmision();
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return pagina;
//    }
//
//    public String modificarProcesoAdm() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
////                ProcesoAdmisionService procesoAdmisionService = BeanFactory.getProcesoAdmisionService();
////                procesoAdmisionService.modificarProcesoAdm(procesoAdmisionBean);
////                listaProcesoAdmisionBean = procesoAdmisionService.obtenerProcesoAdmision();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                limpiarProcesoAdmision();
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return pagina;
//    }
//
//    public String eliminarProcesoAdm() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
////                ProcesoAdmisionService procesoAdmisionService = BeanFactory.getProcesoAdmisionService();
////                procesoAdmisionService.eliminarProcesoAdm(procesoAdmisionBean.getIdProcesoAdmision());
////                listaProcesoAdmisionBean = procesoAdmisionService.obtenerProcesoAdmision();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return pagina;
//    }
//
//    public void guardarProceso() {
//        if (procesoAdmisionBean.getIdProcesoAdmision() == null) {
//            insertarProcesoAdm();
//        } else {
//            modificarProcesoAdm();
//        }
//    }
//
//    public void rowSelect(SelectEvent event) {
//        try {
//            procesoAdmisionBean = (ProcesoAdmisionBean) event.getObject();
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void ponerPorcesoAdmision(Object procesoAdmision) {
//        procesoAdmisionBean = (ProcesoAdmisionBean) procesoAdmision;
//    }
//    
//
//    //Getter y Setter
//    public ProcesoAdmisionBean getProcesoAdmisionBean() {
//        if (procesoAdmisionBean == null) {
//            procesoAdmisionBean = new ProcesoAdmisionBean();
//        }
//        return procesoAdmisionBean;
//    }
//
//    public void setProcesoAdmisionBean(ProcesoAdmisionBean procesoAdmisionBean) {
//        this.procesoAdmisionBean = procesoAdmisionBean;
//    }
//
//    public List<ProcesoAdmisionBean> getListaProcesoAdmisionBean() {
//        if (listaProcesoAdmisionBean == null) {
//            listaProcesoAdmisionBean = new ArrayList<>();
//        }
//        return listaProcesoAdmisionBean;
//    }
//
//    public void setListaProcesoAdmisionBean(List<ProcesoAdmisionBean> listaProcesoAdmisionBean) {
//        this.listaProcesoAdmisionBean = listaProcesoAdmisionBean;
//    }
//    public List<Integer> getListaAnios() {
//        return listaAnios;
//    }
//    public void setListaAnios(List<Integer> listaAnios) {
//        this.listaAnios = listaAnios;
//    }  
}
