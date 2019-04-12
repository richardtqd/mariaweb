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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.CajeroBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaService;
import pe.marista.sigma.service.CajeroService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public final class CajeroMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CajeroMB
     */
    @PostConstruct
    public void CajeroMB() {
        try {
            cargarLista();
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroService cajeroService = BeanFactory.getCajeroService();
            listaEstado = new LinkedHashMap<>();
            listaEstado.put(MaristaConstantes.ESTADO_ACTIVO_DES, MaristaConstantes.ESTADO_ACTIVO);
            listaEstado.put(MaristaConstantes.ESTADO_INACTIVO_DES, MaristaConstantes.ESTADO_INACTIVO);
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            VistaBean vista = new VistaBean();
            vista.getPerfilModuloBean().getPerfilBean().setNombre(MaristaConstantes.PER_CAJERO);
            vista.setUsuarioBean(user);
            listaUsuario = usuarioService.obtenerUsuarioPorPerfil(vista);
            listaUsuariosConCaja = cajeroService.obtenerUsuarioConCaja(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private List<CajeroBean> listaCajeroBean;
    private CajeroBean cajeroBean;
    private CajeroBean cajeroFiltroBean;
    private DualListModel<CajaBean> dualListaCajaBean;
    private List<CajaBean> listaCajaBean;
    private List<CajaBean> listaCajaDest;
    private Map<String, Integer> listaEstado;
    private PersonalBean personalBean;
    List<PersonalBean> listaPersonalBean;
    List<PersonalBean> listaFiltroPersonalBean;
    private CajeroCajaBean cajeroCajaBean;
    private List<VistaBean> listaUsuario;
    private List<CajeroCajaBean> listaUsuariosConCaja;
    private String disabled = "false";

    //Metodos de aplicacion
     public final void actualizarListaCajero() {
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            VistaBean vista = new VistaBean();
            vista.getPerfilModuloBean().getPerfilBean().setNombre(MaristaConstantes.PER_CAJERO);
            vista.setUsuarioBean(user);
            listaUsuario = usuarioService.obtenerUsuarioPorPerfil(vista);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
     
    public final void cargarLista() {
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaCajaBean = new ArrayList<>();
            listaCajaDest = new ArrayList<>();
            CajaService cajaService = BeanFactory.getCajaService();
            listaCajaBean = cajaService.obtenerTodosActivos(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualListaCajaBean = new DualListModel<>(listaCajaBean, listaCajaDest);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<PersonalBean> completeTheme(String query) {
        try {
            CajeroService cajeroService = BeanFactory.getCajeroService();
            listaPersonalBean = new ArrayList<>();
            listaPersonalBean = cajeroService.obtenerUsarioPerfil();
            listaFiltroPersonalBean = new ArrayList<>();
            for (int i = 0; i < listaPersonalBean.size(); i++) {
                PersonalBean skin = listaPersonalBean.get(i);
                if (skin.getNombreCompleto().toLowerCase().contains(query)) {
                    listaFiltroPersonalBean.add(skin);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaFiltroPersonalBean;
    }

    public void obtenerTodos() {
        try {
            CajeroService cajeroService = BeanFactory.getCajeroService();
            listaCajeroBean = new ArrayList<>();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCajerobean() {
        cajeroBean = new CajeroBean();
        personalBean = new PersonalBean();
        cajeroCajaBean = new CajeroCajaBean();
        this.disabled = "false";
        cargarLista();
    }

    public void obtenerPorId(CajeroCajaBean cajeroCaja) {
        try {
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean = cajeroService.obtenerUsuarioConCajaPorId(cajeroCaja);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCajero() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                CajeroService cajeroService = BeanFactory.getCajeroService();
                cajeroCajaBean.setUnidadNegocioBean(user.getPersonalBean().getUnidadNegocioBean());
                cajeroCajaBean.setCreaFecha(formato.parse(date));
                cajeroCajaBean.setCreaPor(user.getUsuario());
                cajeroService.insertar(dualListaCajaBean.getTarget(), cajeroCajaBean);
                limpiarCajerobean();
                cargarLista();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaUsuariosConCaja = cajeroService.obtenerUsuarioConCaja(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarCajero() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CajeroService cajeroService = BeanFactory.getCajeroService();
                UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                cajeroCajaBean.setModiPor(user.getUsuario());
                cajeroService.modificarCajeroCaja(cajeroCajaBean);
                limpiarCajerobean();
                cargarLista();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaUsuariosConCaja = cajeroService.obtenerUsuarioConCaja(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarCajero() {
        if (cajeroCajaBean.getUnidadNegocioBean().getUniNeg() == null) {
            insertarCajero();
        } else if ("".equals(cajeroCajaBean.getUnidadNegocioBean().getUniNeg())) {
            insertarCajero();
        } else {
            modificarCajero();
        }
    }
//

    public String eliminarCajero() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CajeroService cajeroService = BeanFactory.getCajeroService();
                cajeroService.eliminarCajaAll(cajeroCajaBean);
                limpiarCajerobean();
                cargarLista();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaUsuariosConCaja = cajeroService.obtenerUsuarioConCaja(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }
//    public String cambiarEstadoCajero() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                CajeroService cajeroService = BeanFactory.getCajeroService();
//                cajeroService.cambiarEstado(cajeroBean);
//                listaCajeroBean = cajeroService.obtenerTodos();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    }

    public void rowSelect2(SelectEvent event) {
        try {
            personalBean = (PersonalBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            CajeroService cajeroService = BeanFactory.getCajeroService();
            cajeroCajaBean = (CajeroCajaBean) event.getObject();
            listaCajaBean = new ArrayList<>();
            listaCajaDest = new ArrayList<>();
            listaCajaBean = cajeroService.obtenerCajaSinCajero(cajeroCajaBean);
            listaCajaDest = cajeroService.obtenerCajasPorCajero(cajeroCajaBean);
            dualListaCajaBean = null;
            dualListaCajaBean = new DualListModel<>(listaCajaBean, listaCajaDest);
            disabled = "true";
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCajero(Object cajeroCaja) {
        try {
            cajeroCaja = (CajeroCajaBean) cajeroCaja;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void generarToken() {
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        for (int i = 0; i < 10; i++) {
            System.currentTimeMillis();
        }
        int token = usuarioBean.getClave().hashCode();
        cajeroBean.setToken(null);
    }

    //GEtter y Setter
    public List<CajeroBean> getListaCajeroBean() {
        if (listaCajeroBean == null) {
            listaCajeroBean = new ArrayList<>();
        }
        return listaCajeroBean;
    }

    public void setListaCajeroBean(List<CajeroBean> listaCajeroBean) {
        this.listaCajeroBean = listaCajeroBean;
    }

    public CajeroBean getCajeroBean() {
        if (cajeroBean == null) {
            cajeroBean = new CajeroBean();
        }
        return cajeroBean;
    }

    public void setCajeroBean(CajeroBean cajeroBean) {
        this.cajeroBean = cajeroBean;
    }

    public List<CajaBean> getListaCajaBean() {
        if (listaCajaBean == null) {
            listaCajaBean = new ArrayList<>();
        }
        return listaCajaBean;
    }

    public void setListaCajaBean(List<CajaBean> listaCajaBean) {
        this.listaCajaBean = listaCajaBean;
    }

    public List<CajaBean> getListaCajaDest() {
        if (listaCajaDest == null) {
            listaCajaDest = new ArrayList<>();
        }
        return listaCajaDest;
    }

    public void setListaCajaDest(List<CajaBean> listaCajaDest) {
        this.listaCajaDest = listaCajaDest;
    }

    public CajeroBean getCajeroFiltroBean() {
        if (cajeroFiltroBean == null) {
            cajeroFiltroBean = new CajeroBean();
        }
        return cajeroFiltroBean;
    }

    public void setCajeroFiltroBean(CajeroBean cajeroFiltroBean) {
        this.cajeroFiltroBean = cajeroFiltroBean;
    }

    public DualListModel<CajaBean> getDualListaCajaBean() {
        if (dualListaCajaBean == null) {
            dualListaCajaBean = new DualListModel<>();
        }
        return dualListaCajaBean;
    }

    public void setDualListaCajaBean(DualListModel<CajaBean> dualListaCajaBean) {
        this.dualListaCajaBean = dualListaCajaBean;
    }

    public Map<String, Integer> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(Map<String, Integer> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public List<PersonalBean> getListaPersonalBean() {
        return listaPersonalBean;
    }

    public void setListaPersonalBean(List<PersonalBean> listaPersonalBean) {
        this.listaPersonalBean = listaPersonalBean;
    }

    public List<PersonalBean> getListaFiltroPersonalBean() {
        return listaFiltroPersonalBean;
    }

    public void setListaFiltroPersonalBean(List<PersonalBean> listaFiltroPersonalBean) {
        this.listaFiltroPersonalBean = listaFiltroPersonalBean;
    }

    public CajeroCajaBean getCajeroCajaBean() {
        if (cajeroCajaBean == null) {
            cajeroCajaBean = new CajeroCajaBean();
        }
        return cajeroCajaBean;
    }

    public void setCajeroCajaBean(CajeroCajaBean cajeroCajaBean) {
        this.cajeroCajaBean = cajeroCajaBean;
    }

    public List<VistaBean> getListaUsuario() {
        if (listaUsuario == null) {
            listaUsuario = new ArrayList<>();
        }
        return listaUsuario;
    }

    public void setListaUsuario(List<VistaBean> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public List<CajeroCajaBean> getListaUsuariosConCaja() {
        if (listaUsuariosConCaja == null) {
            listaUsuariosConCaja = new ArrayList<>();
        }
        return listaUsuariosConCaja;
    }

    public void setListaUsuariosConCaja(List<CajeroCajaBean> listaUsuariosConCaja) {
        this.listaUsuariosConCaja = listaUsuariosConCaja;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

}
