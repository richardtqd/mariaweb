/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperRunManager;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilModuloBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.PerfilRepBean;
import pe.marista.sigma.bean.reporte.UsuarioRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ModuloService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class PerfilMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of PerfilMB
     */
    @PostConstruct
    public void PerfilMB() {
        usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        cargarLista();
    }

    private PerfilBean perfilBean;
    private DualListModel<ModuloBean> dualListaModuloBean;
    private List<ModuloBean> listaModuloBean;
    private List<ModuloBean> listaModuloDest;
    private PerfilBean perfilFiltroBean;
    private List<PerfilBean> listaPerfilBean;
    private UsuarioBean usuarioLoginBean;

    //Metodos Logica de Negocio
    public final void cargarLista() {
        try {

            listaModuloBean = new ArrayList<>();
            listaModuloDest = new ArrayList<>();
            ModuloService moduloService = BeanFactory.getModuloService();
            listaModuloBean = moduloService.obtenerFiltro(89);
            dualListaModuloBean = new DualListModel<>(listaModuloBean, listaModuloDest);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodos() {
        try {
            PerfilService perfilService = BeanFactory.getPerfilService();
            listaPerfilBean = perfilService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltro() {
        try {
            PerfilService perfilService = BeanFactory.getPerfilService();
            if (perfilFiltroBean.getNombre() != null) {
                perfilFiltroBean.setNombre(perfilFiltroBean.getNombre().toUpperCase().trim());
            }

            listaPerfilBean = perfilService.obtenerFiltro(perfilFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Integer idPerfil) {
        try {
            PerfilService perfilService = BeanFactory.getPerfilService();
            getPerfilBean().setIdPerfil(idPerfil);
            perfilBean = perfilService.obtenerPorId(perfilBean);
            List<PerfilModuloBean> listaPerfilModulo = perfilService.obtenerPorPerfil(perfilBean);
            cargarLista();
            for (int i = 0; i < listaPerfilModulo.size(); i++) {
                ModuloBean moduloBean = new ModuloBean();
                moduloBean.setIdModulo(listaPerfilModulo.get(i).getIdModulo());
                moduloBean.setNodo(listaPerfilModulo.get(i).getNodo());
                moduloBean.setUrl(listaPerfilModulo.get(i).getUrl());
                moduloBean.setIdTipoNodo(listaPerfilModulo.get(i).getIdTipoNodo());
                moduloBean.setIdModuloPadre(listaPerfilModulo.get(i).getIdModuloPadre());
                listaModuloDest.add(moduloBean);
                for (int j = 0; j < listaModuloBean.size(); j++) {
                    if (listaModuloBean.get(j).getIdModulo().toString().equals(moduloBean.getIdModulo().toString())) {
                        System.out.println("idModulo 1:" + listaModuloBean.get(j).getIdModulo() + "idModulo 2:" + moduloBean.getIdModulo());
                        listaModuloBean.remove(j);
                        break;
                    }
                }
            }
            dualListaModuloBean = new DualListModel<>(listaModuloBean, listaModuloDest);

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
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PerfilService perfilService = BeanFactory.getPerfilService();
                perfilBean.setCreaPor(beanUsuarioSesion.getUsuario());
                perfilService.insertarPerfil(perfilBean, dualListaModuloBean.getTarget());
                listaPerfilBean = perfilService.obtenerTodos();
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
                PerfilService perfilService = BeanFactory.getPerfilService();
                perfilBean.setModiPor(usuarioLoginBean.getUsuario());
                perfilService.modificarPerfil(perfilBean, dualListaModuloBean.getTarget());
                listaPerfilBean = perfilService.obtenerTodos();
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
                PerfilService perfilService = BeanFactory.getPerfilService();
                perfilService.eliminarPerfil(perfilBean);
                listaPerfilBean = perfilService.obtenerTodos();
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
                PerfilService perfilService = BeanFactory.getPerfilService();
                if (perfilBean.getEstado2()) {
                    perfilBean.setStatus(1);
                } else {
                    perfilBean.setStatus(0);
                }
                perfilService.cambiarEstado(perfilBean);
                listaPerfilBean = perfilService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerPerfil(Object perfil) {
//        String pagina = null;
        try {
            perfilBean = (PerfilBean) perfil;
        } catch (Exception err) {
//            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarPerfilBean() {
        perfilBean = new PerfilBean();
        cargarLista();
    }

//      public void imprimirTodosPdf() { 
//        ServletOutputStream out = null;
//        try {
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
//                    getResponse();
//            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
//                    getRequest()).getServletContext().getRealPath("/reportes/repPerfiles.jasper");
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            String absoluteWebPath = externalContext.getRealPath("/");
//            File file = new File(archivoJasper);
//
//            List<PerfilRepBean> listaPerfilRepBean = new ArrayList<>();
//            for (PerfilBean listaP : listaPerfilBean) {
//                PerfilRepBean perfilRepBean = new PerfilRepBean();
//                perfilRepBean.setCreapor(listaP.getCreaPor());
//                if(listaP.getCreaFecha() == null){
//                perfilRepBean.setCreafecha(new Timestamp(new Date().getTime()));
//                }else{perfilRepBean.setCreafecha(new Timestamp(listaP.getCreaFecha().getTime()));}
//                              
//                perfilRepBean.setNombre(listaP.getNombre());
//                perfilRepBean.setStatus(listaP.isEstado());
//                listaPerfilRepBean.add(perfilRepBean);
//            }
//            
//            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
//            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPerfilRepBean);
//            Map<String, Object> parametros = new HashMap<>();
//            String ruta = absoluteWebPath + "reportes\\jasper\\";
//            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
////            parametros.put("SUBREPORT_DIR", ruta);
////            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
////            parametros.put("USUARIO", ub.getUsuario());
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
    public PerfilBean getPerfilBean() {
        if (perfilBean == null) {
            perfilBean = new PerfilBean();
        }
        return perfilBean;
    }

    public void setPerfilBean(PerfilBean perfilBean) {
        this.perfilBean = perfilBean;
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

    public DualListModel<ModuloBean> getDualListaModuloBean() {
        if (dualListaModuloBean == null) {
            dualListaModuloBean = new DualListModel<>();
        }
        return dualListaModuloBean;
    }

    public void setDualListaModuloBean(DualListModel<ModuloBean> dualListaModuloBean) {
        this.dualListaModuloBean = dualListaModuloBean;
    }

    public List<ModuloBean> getListaModuloDest() {
        if (listaModuloDest == null) {
            listaModuloDest = new ArrayList<>();
        }
        return listaModuloDest;
    }

    public void setListaModuloDest(List<ModuloBean> listaModuloDest) {
        this.listaModuloDest = listaModuloDest;
    }

    public PerfilBean getPerfilFiltroBean() {
        if (perfilFiltroBean == null) {
            perfilFiltroBean = new PerfilBean();
        }
        return perfilFiltroBean;
    }

    public void setPerfilFiltroBean(PerfilBean perfilFiltroBean) {
        this.perfilFiltroBean = perfilFiltroBean;
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
}
