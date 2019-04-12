package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CheckListBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ProcesoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
//import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ProcesoService;
//import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class ProcesoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ProcesoService
     */
    @PostConstruct
    public void ProcesoMB() {
        try {
            cargarAnio();
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCodigoBean();
            listaCodigoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_COD_PROC));

            //año actual
            Calendar miCalendario = Calendar.getInstance();
            getProcesoBean().setAnio(miCalendario.get(Calendar.YEAR));
            //fecha actual
            fechaInicio = new GregorianCalendar();
            getProcesoBean().setFecIni(fechaInicio.getTime());

            getListaTipoCopiaBean();
            listaTipoCopiaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_COPIA));

            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Variables y Propiedades
    private ProcesoBean procesoBean;
    private List<ProcesoBean> listaProcesoBean;
    private List<Integer> listaAnios;
    private List<CodigoBean> listaCodigoBean;

    //CheckList
    private CheckListBean checkListBean;
    private List<CheckListBean> listaCheckListBean;
    private List<CodigoBean> listaTipoCopiaBean;
    private Boolean checkTodos=false;

    //usuario
    private UsuarioBean usuarioLoginBean;
    private Calendar fechaInicio;

    public void limpiarProceso() {
        procesoBean = new ProcesoBean();
    }

    public void cargarAnio() {
        try {
            int fin = 2020;
            int inicio = 2005;
            listaAnios = new ArrayList<>();
            for (int i = inicio; i <= fin; i++) {
                listaAnios.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProceso() {
        try {
            ProcesoService procesoService = BeanFactory.getProcesoService();
            listaProcesoBean = procesoService.obtenerProceso();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProcesoPorUniNeg() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoService procesoService = BeanFactory.getProcesoService();
            listaProcesoBean = procesoService.obtenerProcesoPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarProceso() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoService procesoService = BeanFactory.getProcesoService();
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                procesoBean.setCreaPor(usuarioLoginBean.getUsuario());
                procesoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                procesoService.insertarProceso(procesoBean);
                listaProcesoBean = procesoService.obtenerProcesoPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarProceso();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarProceso() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ProcesoService procesoService = BeanFactory.getProcesoService();
                procesoBean.setModiPor(usuarioLoginBean.getUsuario());
                procesoService.modificarProceso(procesoBean);
                listaProcesoBean = procesoService.obtenerProcesoPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarProceso();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarProceso() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ProcesoService procesoService = BeanFactory.getProcesoService();
                procesoService.eliminarProceso(procesoBean);
                listaProcesoBean = procesoService.obtenerProcesoPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoService procesoService = BeanFactory.getProcesoService();
                if (procesoBean.getEstadoBoolean()) {
                    procesoBean.setStatus(0);
                } else {
                    procesoBean.setStatus(1);
                }
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                procesoService.cambiarEstado(procesoBean);
                listaProcesoBean = procesoService.obtenerProcesoPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarProceso() {
        if (procesoBean.getIdProceso() != null) {
            modificarProceso();
        } else {
            insertarProceso();
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            procesoBean = (ProcesoBean) event.getObject();
            ProcesoService procesoService = BeanFactory.getProcesoService();
            procesoBean = procesoService.obtenerProcPorId(procesoBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerPorceso(Object proceso) {
        try {
            procesoBean = (ProcesoBean) proceso;
            ProcesoService procesoService = BeanFactory.getProcesoService();
            procesoBean = procesoService.obtenerProcPorId(procesoBean);
             
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object proceso) {
        try {
            procesoBean = (ProcesoBean) proceso;
            ProcesoService procesoService = BeanFactory.getProcesoService();
            procesoBean = procesoService.obtenerProcPorId(procesoBean);
            listaCheckListBean = procesoService.obtenerCheckListPorProceso(procesoBean);
            limpiarCheckList();
            if (listaCheckListBean.isEmpty()) {
                checkListBean.setCollapsed(false);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Check List
    public void limpiarCheckList() {
        checkListBean = new CheckListBean();
        this.checkTodos=false;
        cambiarCheckTodos();
    }

    public String insertarCheckList() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoService procesoService = BeanFactory.getProcesoService();
                checkListBean.setCreaPor(usuarioLoginBean.getUsuario());
                checkListBean.setProcesoBean(procesoBean);
                procesoService.insertarCheckList(checkListBean);
                listaCheckListBean = procesoService.obtenerCheckListPorProceso(procesoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarCheckList();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCheckList() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoService procesoService = BeanFactory.getProcesoService();
                checkListBean.setModiPor(usuarioLoginBean.getUsuario());
                checkListBean.setProcesoBean(procesoBean);
                procesoService.modificarCheckList(checkListBean);
                listaCheckListBean = procesoService.obtenerCheckListPorProceso(procesoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarCheckList();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarCheckList() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoService procesoService = BeanFactory.getProcesoService();
                procesoService.eliminarCheckList(checkListBean);
                listaCheckListBean = procesoService.obtenerCheckListPorProceso(procesoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarCheckList();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarCheckList() {
        if (checkListBean.getIdCheckList() != null) {
            modificarCheckList();
        } else {
            insertarCheckList();
        }
    }

    public void cambiarCheckTodos() {
        try {
            if (checkTodos) {
                checkListBean.setFlg01(true);
                checkListBean.setFlg02(true);
                checkListBean.setFlg03(true);
                checkListBean.setFlg04(true);
                checkListBean.setFlg05(true);
                checkListBean.setFlg06(true);
                checkListBean.setFlg07(true);
                checkListBean.setFlg08(true);
                checkListBean.setFlg09(true);
                checkListBean.setFlg10(true);
                checkListBean.setFlg11(true);
                checkListBean.setFlg23(true);
                checkListBean.setFlg24(true);
                checkListBean.setFlg25(true); 
            } else {
               checkListBean.setFlg01(false);
                checkListBean.setFlg02(false);
                checkListBean.setFlg03(false);
                checkListBean.setFlg04(false);
                checkListBean.setFlg05(false);
                checkListBean.setFlg06(false);
                checkListBean.setFlg07(false);
                checkListBean.setFlg08(false);
                checkListBean.setFlg09(false);
                checkListBean.setFlg10(false);
                checkListBean.setFlg11(false);
                checkListBean.setFlg23(false);
                checkListBean.setFlg24(false);
                checkListBean.setFlg25(false);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectCheckList(SelectEvent event) {
        try {
            checkListBean = (CheckListBean) event.getObject();
            ProcesoService procesoService = BeanFactory.getProcesoService();
            checkListBean = procesoService.obtenerCheckListPorId(checkListBean);
            checkListBean.setCollapsed(false);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCheckList(Object check) {
        try {
            checkListBean = (CheckListBean) check;
            ProcesoService procesoService = BeanFactory.getProcesoService();
            checkListBean = procesoService.obtenerCheckListPorId(checkListBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Getter y Setter
    public ProcesoBean getProcesoBean() {
        if (procesoBean == null) {
            procesoBean = new ProcesoBean();
        }
        return procesoBean;
    }

    public void setProcesoBean(ProcesoBean procesoBean) {
        this.procesoBean = procesoBean;
    }

    public List<ProcesoBean> getListaProcesoBean() {
        if (listaProcesoBean == null) {
            listaProcesoBean = new ArrayList<>();
        }
        return listaProcesoBean;
    }

    public void setListaProcesoBean(List<ProcesoBean> listaProcesoBean) {
        this.listaProcesoBean = listaProcesoBean;
    }

    public List<Integer> getListaAnios() {
        return listaAnios;
    }

    public void setListaAnios(List<Integer> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public List<CodigoBean> getListaCodigoBean() {
        if (listaCodigoBean == null) {
            listaCodigoBean = new ArrayList<>();
        }
        return listaCodigoBean;
    }

    public void setListaCodigoBean(List<CodigoBean> listaCodigoBean) {
        this.listaCodigoBean = listaCodigoBean;
    }

    public CheckListBean getCheckListBean() {
        if (checkListBean == null) {
            checkListBean = new CheckListBean();
        }
        return checkListBean;
    }

    public void setCheckListBean(CheckListBean checkListBean) {
        this.checkListBean = checkListBean;
    }

    public List<CheckListBean> getListaCheckListBean() {
        if (listaCheckListBean == null) {
            listaCheckListBean = new ArrayList<>();
        }
        return listaCheckListBean;
    }

    public void setListaCheckListBean(List<CheckListBean> listaCheckListBean) {
        this.listaCheckListBean = listaCheckListBean;
    }

    public List<CodigoBean> getListaTipoCopiaBean() {
        if (listaTipoCopiaBean == null) {
            listaTipoCopiaBean = new ArrayList<>();
        }
        return listaTipoCopiaBean;
    }

    public void setListaTipoCopiaBean(List<CodigoBean> listaTipoCopiaBean) {
        this.listaTipoCopiaBean = listaTipoCopiaBean;
    }

    public Boolean getCheckTodos() {
        return checkTodos;
    }

    public void setCheckTodos(Boolean checkTodos) {
        this.checkTodos = checkTodos;
    }
}
