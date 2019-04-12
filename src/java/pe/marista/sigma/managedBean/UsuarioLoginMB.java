package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilModuloBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.LoginService;
import pe.marista.sigma.service.ModuloService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class UsuarioLoginMB implements Serializable {

    // Construtores
    @PostConstruct
    public void init() {
        try {
            if (new MaristaUtils().sesionObtenerObjeto("idioma") == null) {
                new MaristaUtils().sesionColocarObjeto("idioma", Locale.getDefault());
            }
            UnidadNegocioService unidadNegocioService = BeanFactory.getUnidadNegocioService();
            listaUnidadNegocioBean = unidadNegocioService.obtenerTodos();
            model = new BaseMenuModel();
            listaUnidadNegocioBean = unidadNegocioService.obtenerTodosDef();
            model = new BaseMenuModel();
            //AYUDA :D  
//            model = new BaseMenuModel();  
            getUsuarioBean().getPersonalBean().getUnidadNegocioBean().setUniNeg("SANJOC");
            String uniNeg = "";
            if (listaUnidadNegocioBean.size() > 0) {
                uniNeg = listaUnidadNegocioBean.get(0).getUniNeg();
            } else {
                listaUnidadNegocioBean = new ArrayList<>();
                listaUnidadNegocioBean = unidadNegocioService.obtenerTodosDef();
            }

            getUsuarioBean().getPersonalBean().getUnidadNegocioBean().setUniNeg(uniNeg);
            model = new BaseMenuModel();
        } catch (Exception ex) {

        }
    }

    // Variables
    private UsuarioBean usuarioBean;
    private MenuModel model;
    private List<PerfilModuloBean> listaPerfilModuloBean;
    //Menu Principal
    private List<ModuloBean> listaModuloBean;
    private ModuloBean moduloBean;
    private final DefaultSeparator linea = new DefaultSeparator();
    private List<UnidadNegocioBean> listaUnidadNegocioBean;

    public String autenticarUsuario() {
        String pagina = null;
        try {
            LoginService loginService = BeanFactory.getLoginService();
            usuarioBean = loginService.autenticarUsuario(usuarioBean);
            if (usuarioBean != null) {
                new MaristaUtils().sesionColocarObjeto("usuarioLogin", usuarioBean);
                obtenerMenuPrincipal();
                String rutaUsuario = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/mantenimientos/mantUsuario.xhtml");
                new MaristaUtils().sesionColocarObjeto("ruta_usuario", rutaUsuario);
                pagina = "toRoot";
                GLTLog.write(GLTLog.CATEGORIA_INFO, this.getClass(), "Usuario ingresó al Sistema");
                personal(usuarioBean);
            } else {
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("errorAutenticacion", null));
                pagina = null;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
            pagina = null;
        }
        return pagina;
    }

    public void personal(UsuarioBean usuarioBean) {
        try {
//            List<PersonalBean> listaPersonalBean = new ArrayList<>();
//            PersonalService personalService = BeanFactory.getPersonalService();

//            listaPersonalBean = personalService.obtenerPersonalCumpleaños(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            if (!listaPersonalBean.isEmpty()) {
//            listaPersonalBean = personalService.obtenerPersonalCumpleanios(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
////            if (!listaPersonalBean.isEmpty()) {
//                for (PersonalBean personal : listaPersonalBean) {
//            listaPersonalBean = personalService.obtenerPersonalCumpleanios(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            if (!listaPersonalBean.isEmpty()) {
//
//                for (PersonalBean personal : listaPersonalBean) {
//                    if (personal.getCorreoCor().trim() != null && !personal.getCorreoCor().trim().equals("")
//                            && personal.getCorreoCor() != null && !personal.getCorreoCor().equals("")) {
////                        new Mailing().enviarCorreoCumple(personal);
//                    }
//                }
//                for (PersonalBean personal : listaPersonalBean) {
////                    if (personal.getCorreoCor().trim() != null && !personal.getCorreoCor().trim().equals("")
////                            && personal.getCorreoCor() != null && !personal.getCorreoCor().equals("")) {
////                        new Mailing().enviarCorreoCumple(personal);
//                    }
//                }
//            }
//            } 
//
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void checkPermissions(ComponentSystemEvent event) {
        UsuarioBean bean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        if (bean == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
            nav.performNavigation("logOut");
        }
    }

    public UsuarioBean getBeanLoginUsuario() {
        UsuarioBean nn = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        return nn;
    }

    public String salir() {
        new MaristaUtils().sesionColocarObjeto("usuarioLogin", null);
        HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().
                getExternalContext().getRequest()).getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "logOut";
    }

    private void cargarVariablesDeMenu(LoginService loginService, Integer codigo) throws Exception {
        PerfilService perfilService = BeanFactory.getPerfilService();
        List<PerfilModuloBean> listaModuloTodosBean = perfilService.obtenerModulo();
        model = new BaseMenuModel();
        DefaultMenuItem menu = new DefaultMenuItem();
        menu.setValue("Panel de Control");
        menu.setUrl("/faces/panControl.xhtml");
        model.addElement(menu);
        DefaultMenuItem sepa = new DefaultMenuItem();
        sepa.setValue("||");
        sepa.setDisabled(true);
        sepa.setStyle("padding-right: 15px ; padding-left: 15px");
        model.addElement(sepa);
        List<VistaBean> listaVistaBean = loginService.obtenerVistaPorUsuario(usuarioBean);
        List<ModuloBean> listaModuloTotal = new ArrayList<>();
        for (int i = 0; i < listaVistaBean.size(); i++) {
            List<ModuloBean> listaModuloBean = obtenerModuloPorPerfil(listaModuloTodosBean, listaVistaBean.get(i).getPerfilModuloBean().getIdPerfil());
            for (int j = 0; j < listaModuloBean.size(); j++) {
                if (!existe(listaModuloTotal, listaModuloBean.get(j).getIdModulo())) {
                    listaModuloTotal.add(listaModuloBean.get(j));
                }
            }
        }
        insertarPerfil(listaModuloTotal);
        List<VistaBean> listaVistaTotal = new ArrayList<>();
        for (int i = 0; i < listaPerfilModuloBean.size(); i++) {
            VistaBean bean = new VistaBean();
            bean.setPerfilModuloBean(listaPerfilModuloBean.get(i));
            listaVistaTotal.add(bean);
        }
        for (int i = 0; i < listaVistaTotal.size(); i++) {
            if (listaVistaTotal.get(i).getPerfilModuloBean().getModuloBean().getIdModuloPadre().toString().equals(codigo.toString())) {
                DefaultSubMenu submenu = new DefaultSubMenu();
                submenu.setLabel(listaVistaTotal.get(i).getPerfilModuloBean().getModuloBean().getNodo());
                mappearVistaRecursive(listaVistaTotal, listaVistaTotal.get(i).getPerfilModuloBean().getModuloBean().getIdModulo(), submenu);
                model.addElement(submenu);
                model.addElement(sepa);
            }
        }
        new MaristaUtils().sesionColocarObjeto("model", model);
    }

    public List<ModuloBean> obtenerModuloPorPerfil(List<PerfilModuloBean> listaPerfilModuloBean, Integer idPerfil) {
        List<ModuloBean> listaModuloBean = null;
        try {
            for (int i = 0; i < listaPerfilModuloBean.size(); i++) {
                if (i == 0) {
                    listaModuloBean = new ArrayList<>();
                }
                if (Objects.equals(listaPerfilModuloBean.get(i).getIdPerfil(), idPerfil)) {
                    ModuloBean modulo = new ModuloBean();
                    modulo.setIdModulo(listaPerfilModuloBean.get(i).getIdModulo());
                    modulo.setNodo(listaPerfilModuloBean.get(i).getNodo());
                    modulo.setUrl(listaPerfilModuloBean.get(i).getUrl());
                    modulo.setIdTipoNodo(listaPerfilModuloBean.get(i).getIdTipoNodo());
                    modulo.setIdModuloPadre(listaPerfilModuloBean.get(i).getIdModuloPadre());
                    listaModuloBean.add(modulo);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaModuloBean;
    }

    public boolean existe(List<ModuloBean> lista, Integer idModulo) {
        boolean rpta = false;
        for (ModuloBean moduloBean : lista) {
            if (moduloBean.getIdModulo().toString().equals(idModulo.toString())) {
                return true;
            }
        }
        return rpta;
    }

    public boolean existe2(Integer idModulo) {
        boolean rpta = false;
        for (PerfilModuloBean perfilModuloBean : listaPerfilModuloBean) {
            if (perfilModuloBean.getModuloBean().getIdModulo().toString().equals(idModulo.toString())) {
                return true;
            }
        }
        return rpta;
    }

    public void mappearVistaRecursive(List<VistaBean> listaVistaBean, Integer idModulo, DefaultSubMenu submenu) {
        for (int i = 0; i < listaVistaBean.size(); i++) {
            if (idModulo.toString().equals(listaVistaBean.get(i).getPerfilModuloBean().getModuloBean().getIdModuloPadre().toString())) {
                if (listaVistaBean.get(i).getPerfilModuloBean().getModuloBean().getIdTipoNodo() == 89) {
                    DefaultMenuItem menuChild = new DefaultMenuItem();
                    menuChild.setValue(listaVistaBean.get(i).getPerfilModuloBean().getModuloBean().getNodo());
                    menuChild.setUrl("/faces" + listaVistaBean.get(i).getPerfilModuloBean().getModuloBean().getUrl());
                    submenu.addElement(menuChild);
                    submenu.addElement(linea);
                }
                if (listaVistaBean.get(i).getPerfilModuloBean().getModuloBean().getIdTipoNodo() == 88) {
//                    UISubmenu subMenuChild = new UISubmenu();
                    DefaultSubMenu subMenuChild = new DefaultSubMenu();
                    subMenuChild.setLabel(listaVistaBean.get(i).getPerfilModuloBean().getModuloBean().getNodo());
                    mappearVistaRecursive(listaVistaBean, listaVistaBean.get(i).getPerfilModuloBean().getModuloBean().getIdModulo(), subMenuChild);
                    submenu.addElement(subMenuChild);
                }
            }
        }
    }

    public void insertarPerfil(List<ModuloBean> listaModuloOrig) throws Exception {
        listaPerfilModuloBean = new ArrayList<>();
        ModuloService moduloService = BeanFactory.getModuloService();
        List<ModuloBean> listaModuloBean = moduloService.obtenerTodos();
        for (ModuloBean objecto : listaModuloOrig) {
            PerfilModuloBean perfilModuloBean = new PerfilModuloBean();
//            perfilModuloBean.setPerfilBean(perfilBean);
            perfilModuloBean.getModuloBean().setIdModulo(new Integer(objecto.getIdModulo()));
            perfilModuloBean.getModuloBean().setIdModuloPadre(new Integer(objecto.getIdModuloPadre()));
            perfilModuloBean.getModuloBean().setUrl(objecto.getUrl());
            perfilModuloBean.getModuloBean().setNodo(objecto.getNodo());
            perfilModuloBean.getModuloBean().setIdTipoNodo(objecto.getIdTipoNodo());
            if (!existe2(perfilModuloBean.getModuloBean().getIdModulo())) {
                listaPerfilModuloBean.add(perfilModuloBean);
            }
            insertarPerfilRecursive(listaModuloBean, perfilModuloBean.getModuloBean().getIdModuloPadre());
        }
    }

    public void insertarPerfilRecursive(List<ModuloBean> listaModuloBean, Integer idModuloPadre) throws Exception {
        for (int i = 0; i < listaModuloBean.size(); i++) {
            if (listaModuloBean.get(i).getIdModulo().toString().equals(idModuloPadre.toString())) {
                PerfilModuloBean perfilModuloBean = new PerfilModuloBean();
                perfilModuloBean.getModuloBean().setIdModulo(idModuloPadre);
                perfilModuloBean.getModuloBean().setIdModuloPadre(listaModuloBean.get(i).getIdModuloPadre());
                perfilModuloBean.getModuloBean().setUrl(listaModuloBean.get(i).getUrl());
                perfilModuloBean.getModuloBean().setNodo(listaModuloBean.get(i).getNodo());
                perfilModuloBean.getModuloBean().setIdTipoNodo(listaModuloBean.get(i).getIdTipoNodo());
                try {
                    if (idModuloPadre != 1) {
                        if (!existe2(perfilModuloBean.getModuloBean().getIdModulo())) {
                            listaPerfilModuloBean.add(perfilModuloBean);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ya resgistrado");
                }
                if (idModuloPadre != 1) {
                    insertarPerfilRecursive(listaModuloBean, listaModuloBean.get(i).getIdModuloPadre());
                }
            }
        }
    }

    public void cambiaIdioma(String idioma) {
        if (idioma.equals("EN")) {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
            new MaristaUtils().sesionColocarObjeto("idioma", Locale.ENGLISH);
        }
        if (idioma.equals("ES")) {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.getDefault());
            new MaristaUtils().sesionColocarObjeto("idioma", Locale.getDefault());
        }
    }

    public MenuModel getModel() {
        if (model == null) {
            model = new BaseMenuModel();
        }
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    //Recibo la lista de todos los modulos del usuario(todos los perfiles).
    //Recibo la lsita de todos los modulos del sistema
    public void generaMenu(List<ModuloBean> listaModuloUsuario, List<ModuloBean> listaModuloSistema) {
        ModuloBean moduloInicial = listaModuloUsuario.get(0);

    }

    public void encuentraHijos(List<ModuloBean> listaModuloBean, ModuloBean moduloBean, DefaultSubMenu submenu) {
        for (int i = 0; i < listaModuloBean.size(); i++) {
            if (listaModuloBean.get(i).getIdModuloPadre() == moduloBean.getIdModulo()) {
                DefaultMenuItem menuChild = new DefaultMenuItem();
                menuChild.setValue(listaModuloBean.get(i).getNodo());
                menuChild.setUrl(listaModuloBean.get(i).getUrl());
                submenu.addElement(menuChild);
                encuentraHijos(listaModuloBean, listaModuloBean.get(i), submenu);
            }
            for (int j = 0; j < listaModuloBean.size(); j++) {
                if (listaModuloBean.get(j).getIdModuloPadre() == moduloBean.getIdModuloPadre() && listaModuloBean.get(j).getIdModulo() != moduloBean.getIdModulo()) {
                    DefaultMenuItem menuChild = new DefaultMenuItem();
                    menuChild.setValue(listaModuloBean.get(j).getNodo());
                    menuChild.setUrl(listaModuloBean.get(j).getUrl());
                    submenu.addElement(menuChild);
                    encuentraHijos(listaModuloBean, listaModuloBean.get(j), submenu);
                }
            }
        }
    }

    public void encuentraPadre(List<ModuloBean> listaModuloBean, ModuloBean moduloBean, DefaultSubMenu submenu) {
        for (int i = 0; i < listaModuloBean.size(); i++) {
            if (listaModuloBean.get(i).getIdModulo() == moduloBean.getIdModuloPadre()) {
                DefaultMenuItem menuChild = new DefaultMenuItem();
                menuChild.setValue(listaModuloBean.get(i).getNodo());
                menuChild.setUrl(listaModuloBean.get(i).getUrl());
                submenu.addElement(menuChild);
            }

        }
    }

    ///------------------------------------------------------------ Menu 2.0 --------------------------------------------------------------///
    private void obtenerMenuPrincipal() throws Exception {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaModuloBean = usuarioService.obtenerModulos();

            List<ModuloBean> lista = new ArrayList<>();
            lista = usuarioService.obtenerMenuPrincipal(usuarioBean.getUsuario(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            for (ModuloBean modulo : listaModuloBean) {
                for (ModuloBean lista1 : lista) {
                    if (Objects.equals(lista1.getIdModulo(), modulo.getIdModulo())) {
                        modulo.setModuloDisable(Boolean.FALSE);
                        break;
                    } else {
                        modulo.setModuloDisable(Boolean.TRUE);
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String obtenerSubMenuPrincipal(Object objeto) {
        String pagina = null;
        try {
            ModuloBean modulo = (ModuloBean) objeto;
            moduloBean = modulo;
            LoginService loginService = BeanFactory.getLoginService();
//            Integer codigo = (Integer) objeto;
            cargarVariablesDeMenu(loginService, modulo.getIdModulo());
            return "toRootMenu";
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
            return null;
        }
    }

    public void showModal() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('myDialogVar').show();");
    }

    // Metodos Getter y Setter
    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public List<PerfilModuloBean> getListaPerfilModuloBean() {
        if (listaPerfilModuloBean == null) {
            listaPerfilModuloBean = new ArrayList<>();
        }
        return listaPerfilModuloBean;
    }

    public void setListaPerfilModuloBean(List<PerfilModuloBean> listaPerfilModuloBean) {
        this.listaPerfilModuloBean = listaPerfilModuloBean;
    }

    public List<ModuloBean> getListaModuloBean() {
        if (listaModuloBean == null) {
            listaModuloBean = new ArrayList<>();
        }
        return listaModuloBean;
    }

    public void setListaModuloBean(List<ModuloBean> listaModuloBean) {
        this.listaModuloBean = listaModuloBean;
    }

    public ModuloBean getModuloBean() {
        if (moduloBean == null) {
            moduloBean = new ModuloBean();
        }
        return moduloBean;
    }

    public void setModuloBean(ModuloBean moduloBean) {
        this.moduloBean = moduloBean;
    }

    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
        if (listaUnidadNegocioBean == null) {
            listaUnidadNegocioBean = new ArrayList<>();
        }
        return listaUnidadNegocioBean;
    }

    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
    }

}
