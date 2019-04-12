/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperRunManager;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class UsuarioMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of UsuarioMB
     */
    @PostConstruct
    public void UsuarioMB() {
        try {
            cargarLista();
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }
    private List<UsuarioBean> listaUsuarioBean;
    private UsuarioBean usuarioBean;
    private PersonalBean personalBean;
    private List<PersonalBean> listaPersonalBean;
    private DualListModel<PerfilBean> dualListaPerfilBean;
    private List<PerfilBean> listaPerfilBean;
    private List<PerfilBean> listaPerfilDest;
    private UsuarioBean usuarioFiltroBean;
    private UsuarioBean usuarioLogin;
    private Integer[] selectedIdTipoAcceso;

    private List<CodigoBean> listaTipoAccesoBean;

    //Metodos Logica de Negocio
    public final void cargarLista() {
        try {
            listaPerfilBean = new ArrayList<>();
            listaPerfilDest = new ArrayList<>();
            PerfilService perfilService = BeanFactory.getPerfilService();
            listaPerfilBean = perfilService.obtenerTodos();
            dualListaPerfilBean = new DualListModel<>(listaPerfilBean, listaPerfilDest);
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoAccesoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ACCESO));

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodos() {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaUsuarioBean = usuarioService.obtenerPorUnidadNegocio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltro() {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            if (usuarioFiltroBean.getUsuario() != null) {
                usuarioFiltroBean.setUsuario(usuarioFiltroBean.getUsuario().toUpperCase().trim());
            }
            if (usuarioFiltroBean.getPersonalBean().getNombre() != null) {
                usuarioFiltroBean.getPersonalBean().setNombre(usuarioFiltroBean.getPersonalBean().getNombre().toUpperCase().trim());
            }
            if (usuarioFiltroBean.getPersonalBean().getApepat() != null) {
                usuarioFiltroBean.getPersonalBean().setApepat(usuarioFiltroBean.getPersonalBean().getApepat().toUpperCase().trim());
            }
            if (usuarioFiltroBean.getPersonalBean().getApemat() != null) {
                usuarioFiltroBean.getPersonalBean().setApemat(usuarioFiltroBean.getPersonalBean().getApemat().toUpperCase().trim());
            }
            listaUsuarioBean = usuarioService.obtenerPorFiltro(usuarioFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void enviarUsu() {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            usuarioBean = usuarioService.buscarPorId(usuarioLogin.getUsuario());
            usuarioBean.setEdita(1);
            usuarioBean.setClave(null);
            usuarioBean.setClave2(null);
            usuarioBean.setClaveAnterior(null);
//            List<PerfilBean> listaPerfil = usuarioService.obtenerVistaPerfilPorUsuario(usuarioBean);
//            cargarLista();
//            for (int i = 0; i < listaPerfil.size(); i++) {
//                listaPerfilDest.add(listaPerfil.get(i));
//                for (int j = 0; j < listaPerfilBean.size(); j++) {
//                    if (Objects.equals(listaPerfilBean.get(j).getIdPerfil(), listaPerfil.get(i).getIdPerfil())) {
//                        listaPerfilBean.remove(j);
//                    }
//                }
//            }
//            dualListaPerfilBean = new DualListModel<>(listaPerfilBean, listaPerfilDest);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(String usuario) {
        try {

            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            usuarioBean = usuarioService.buscarPorId(usuario);
            usuarioBean.setEdita(1);
            List<PerfilBean> listaPerfil = usuarioService.obtenerVistaPerfilPorUsuario(usuarioBean);
            cargarLista();
            for (int i = 0; i < listaPerfil.size(); i++) {
                listaPerfilDest.add(listaPerfil.get(i));
                for (int j = 0; j < listaPerfilBean.size(); j++) {
                    if (Objects.equals(listaPerfilBean.get(j).getIdPerfil(), listaPerfil.get(i).getIdPerfil())) {
                        listaPerfilBean.remove(j);
                    }
                }
            }
            dualListaPerfilBean = new DualListModel<>(listaPerfilBean, listaPerfilDest);
            List<Integer> listaTipoAccesoUsu = new ArrayList<>();
            listaTipoAccesoUsu = usuarioService.obtenerTipoNivelAccesoPorUsuario(usuario, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            Integer size = listaTipoAccesoUsu.size();
//            if(!size.equals(0)){
//                size=size-1;
//            }
            selectedIdTipoAcceso = new Integer[size];
            Integer count = 0;
            for (Integer lis : listaTipoAccesoUsu) {
                selectedIdTipoAcceso[count] = lis;
                count = count + 1;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarUsuario() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
                for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                    listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                    System.out.print(selectedIdTipoAcceso[i] + "\t");
                }
                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                usuarioBean.setCreaPor(usuarioLogin.getUsuario());
                PersonalBean personal = new PersonalBean();
                LegajoService legajoService = BeanFactory.getLegajoService();
                personal.setIdPersonal(usuarioBean.getPersonalBean().getIdPersonal());
                personal.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                personal = legajoService.obtenerLegajoId(personal);
                if (personal.getStatusVista().equals(true)) {
                    usuarioService.insertarUsuario(usuarioBean, dualListaPerfilBean.getTarget(), listaTipoAccesoConsiderar);
                    obtenerTodos();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    new MensajePrime().addInformativeMessagePer("El usuario ya no labora, si desea crear usuario activarlo");
                    System.out.println("No se inserto ");
                }
//                listaUsuarioBean = usuarioService.obtenerTodos(); 
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarUsuario() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
                for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                    listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                    System.out.print(selectedIdTipoAcceso[i] + "\t");
                }

                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                usuarioBean.setModiPor(usuarioLogin.getUsuario());
                usuarioService.modificarUsuario(usuarioBean, dualListaPerfilBean.getTarget(), listaTipoAccesoConsiderar);
//                listaUsuarioBean = usuarioService.obtenerTodos();
                obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarContrasena() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                UsuarioBean usu = usuarioService.buscarPorId(usuarioLogin.getUsuario());
                usu.setEdita(1);
                System.out.println("clave antt1..." + usu.getClave());
                System.out.println("clave antt2..." + usuarioBean.getClaveAnterior());
                if (usu.getClave().equals(usuarioBean.getClaveAnterior())) {
                    //  es la clave ant 
                    System.out.println("clave ant es igual");
                    System.out.println("clave 1..." + usuarioBean.getClave());
                    System.out.println("clave 2..." + usuarioBean.getClave());
                    if (usuarioBean.getClave().equals(usuarioBean.getClave2())) {
                        usuarioBean.setModiPor(usuarioBean.getUsuario());
                        usuarioService.modificarUsuarioContra(usuarioBean);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        //clave no co
                        System.out.println("claveS NO SON IGUALES");
                        new MensajePrime().addInformativeMessagePer("etiquetaContraNoCoinciden");
                    }
                } else {
                    System.out.println("clave ant NO es igual");
                    new MensajePrime().addInformativeMessagePer("etiquetaClaveAntError");
                }

//                listaUsuarioBean = usuarioService.obtenerTodos(); 
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarUsuario() {
        if (usuarioBean.getEdita() == null) {
            insertarUsuario();
        } else {
            modificarUsuario();
        }
    }

    public String eliminarUsuario() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                usuarioService.eliminarUsuario(usuarioBean);
//                listaUsuarioBean = usuarioService.obtenerTodos();
                obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                if (usuarioBean.getEstado2()) {
                    usuarioBean.setStatus(1);
                } else {
                    usuarioBean.setStatus(0);
                }
                usuarioService.cambiarEstado(usuarioBean);
//                listaUsuarioBean = usuarioService.obtenerTodos();
                obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerUsuario(Object usuario) {
//        String pagina = null;
        try {
            usuarioBean = (UsuarioBean) usuario;
        } catch (Exception err) {
//            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPersonalsFiltro() {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            if (personalBean.getApepat() != null) {
                personalBean.setApepat(personalBean.getApepat().toUpperCase().toString());
            }
            if (personalBean.getApemat() != null) {
                personalBean.setApemat(personalBean.getApemat().toUpperCase().toString());
            }
            if (personalBean.getNombre() != null) {
                personalBean.setNombre(personalBean.getNombre().toUpperCase().toString());
            }
            personalBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaPersonalBean = usuarioService.obtenerPersonalFiltro(personalBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonal(Object object) {
        PersonalBean personall = (PersonalBean) object;
        usuarioBean.setPersonalBean(personall);
    }

    public void limpiarPersonalBean() {
        personalBean = new PersonalBean();
        listaPersonalBean = new ArrayList<>();
    }

    public void limpiarUsuarioBean() {
        usuarioBean = new UsuarioBean();
        cargarLista();
    }

//    public void imprimirTodosPdf() { 
//        ServletOutputStream out = null;
//        try {
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
//                    getResponse();
//            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
//                    getRequest()).getServletContext().getRealPath("/reportes/RepUsuario.jasper");
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            String absoluteWebPath = externalContext.getRealPath("/");
//            File file = new File(archivoJasper);
//
//            List<UsuarioRepBean> listaUsuarioRepBean = new ArrayList<>();
//            for (int i = 0; i < listaUsuarioBean.size(); i++) {
//                UsuarioRepBean usuarioRepBean = new UsuarioRepBean();
//                usuarioRepBean.setUsuario(listaUsuarioBean.get(i).getUsuario());
//                usuarioRepBean.setApepat(listaUsuarioBean.get(i).getPersonalBean().getApepat());
//                usuarioRepBean.setApemat(listaUsuarioBean.get(i).getPersonalBean().getApemat());
//                usuarioRepBean.setNombre(listaUsuarioBean.get(i).getPersonalBean().getNombre());
//                usuarioRepBean.setStatus(listaUsuarioBean.get(i).getStatusVista());
//                usuarioRepBean.setClave(new Integer(listaUsuarioBean.get(i).getClave()));
//                listaUsuarioRepBean.add(usuarioRepBean);
//            }
//            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
//            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaUsuarioRepBean);
//            Map<String, Object> parametros = new HashMap<>();
//            String ruta = absoluteWebPath + "reportes\\jasper\\";
//            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("SUBREPORT_DIR", ruta);
//            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            parametros.put("USUARIO", ub.getUsuario());
//
//            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
//            response.reset();
//            response.setContentType("application/pdf");
//            response.setContentLength(bytes.length);
//            out = response.getOutputStream();
//            out.write(bytes);
//            out.flush();
//
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (Exception ettt) {
//                new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ettt);
//            }
//        }
//        // Inform JSF that it doesn't need to handle response.
//        // This is very important, otherwise you will get the following exception in the logs:
//        // java.lang.IllegalStateException: Cannot forward after response has been committed.
//        FacesContext.getCurrentInstance().responseComplete();
//    }
    //Metodos Getters y Setters
    public List<UsuarioBean> getListaUsuarioBean() {
        if (listaUsuarioBean == null) {
            listaUsuarioBean = new ArrayList<>();
        }
        return listaUsuarioBean;
    }

    public void setListaUsuarioBean(List<UsuarioBean> listaUsuarioBean) {
        this.listaUsuarioBean = listaUsuarioBean;
    }

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
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
        if (listaPersonalBean == null) {
            listaPersonalBean = new ArrayList<>();
        }
        return listaPersonalBean;
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

    public DualListModel<PerfilBean> getDualListaPerfilBean() {
        if (dualListaPerfilBean == null) {
            dualListaPerfilBean = new DualListModel<>();
        }
        return dualListaPerfilBean;
    }

    public void setDualListaPerfilBean(DualListModel<PerfilBean> dualListaPerfilBean) {
        this.dualListaPerfilBean = dualListaPerfilBean;
    }

    public List<PerfilBean> getListaPerfilDest() {
        if (listaPerfilDest == null) {
            listaPerfilDest = new ArrayList<>();
        }
        return listaPerfilDest;
    }

    public void setListaPerfilDest(List<PerfilBean> listaPerfilDest) {
        this.listaPerfilDest = listaPerfilDest;
    }

    public UsuarioBean getUsuarioFiltroBean() {
        if (usuarioFiltroBean == null) {
            usuarioFiltroBean = new UsuarioBean();
        }
        return usuarioFiltroBean;
    }

    public void setUsuarioFiltroBean(UsuarioBean usuarioFiltroBean) {
        this.usuarioFiltroBean = usuarioFiltroBean;
    }

    public List<CodigoBean> getListaTipoAccesoBean() {
        if (listaTipoAccesoBean == null) {
            listaTipoAccesoBean = new ArrayList<>();
        }
        return listaTipoAccesoBean;
    }

    public void setListaTipoAccesoBean(List<CodigoBean> listaTipoAccesoBean) {
        this.listaTipoAccesoBean = listaTipoAccesoBean;
    }

    public Integer[] getSelectedIdTipoAcceso() {
        return selectedIdTipoAcceso;
    }

    public void setSelectedIdTipoAcceso(Integer[] selectedIdTipoAcceso) {
        this.selectedIdTipoAcceso = selectedIdTipoAcceso;
    }
}
