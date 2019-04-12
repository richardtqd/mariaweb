package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CajaChicaMovBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaChicaMovService;
import pe.marista.sigma.service.CajaChicaService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class CajaChicaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CajaChicaMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            VistaBean vista = new VistaBean();
            vista.getPerfilModuloBean().getPerfilBean().setNombre(MaristaConstantes.PER_CAJERO_CCH);
            vista.setUsuarioBean(usuarioLogin);
            listaCajeroBean = usuarioService.obtenerUsuarioPorPerfil(vista);
//            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
//            listaTipoCambioBean = tipoCambioService.obtenerTodosActivos();
            getCajaChicaBean().setFecApertura(new Date());
            cajaChicaBean.setAnio(Calendar.getInstance().get((Calendar.YEAR)));
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoCajaChica = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIPO_CAJACH));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private CajaChicaBean cajaChicaBean;
    private List<CajaChicaBean> listaCajaChicaBean;
    private UsuarioBean usuarioLogin;
    private CajaChicaBean cajaChicaFiltroBean;
    private List<VistaBean> listaCajeroBean;
    private List<CodigoBean> listaTipoCajaChica;
//    private List<TipoCambioBean> listaTipoCambioBean;

    //Logica de Negocio
    public void obtenerCajaChica(String origen) {
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaBean = cajaChicaService.obtenerCajaChica(cajaChicaBean);
//            if (origen.equals("2")) {
//                List<CajaChicaBean> listaAbierto = cajaChicaService.obtenerCajaChicaAbierto(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                if (listaAbierto.size() == 1) {
//                    CajaChicaMovMB cajaChicaMovMB = new 
//                    cajaChicaMovMB.setCajaChicaBean(listaAbierto.get(0));
//                    FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cajaChicaMovMB", cajaChicaMovMB);
//                }
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void obtenerCajaChicaPorFiltro() {
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            cajaChicaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaBean = cajaChicaService.obtenerCajaChicaPorFiltro(cajaChicaFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public String insertarCajaChica() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
                cajaChicaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
//                boolean comprobacion = true;
                List<CajaChicaBean> listaAbierto = new ArrayList<>();
                listaAbierto = cajaChicaService.obtenerCajaChicaAbierto(getCajaChicaBean());
                if (listaAbierto.isEmpty()) {
                    cajaChicaBean.setCreaPor(usuarioLogin.getUsuario());
                    cajaChicaService.insertarCajaChica(cajaChicaBean);
                    listaCajaChicaBean = cajaChicaService.obtenerCajaChica(cajaChicaBean);
                    limpiarCajaChicaBean();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    new MensajePrime().addInformativeMessagePer("msjCajaChicaAbierto");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCajaChica() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
                cajaChicaBean.setModiPor(usuarioLogin.getUsuario());
                cajaChicaService.modificarCajaChica(cajaChicaBean);
                listaCajaChicaBean = cajaChicaService.obtenerCajaChica(cajaChicaBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String elimianrCajaChica() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
                cajaChicaService.eliminarCajaChica(cajaChicaBean);
                listaCajaChicaBean = cajaChicaService.obtenerCajaChica(cajaChicaBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerCajaChicaPorId(Object objeto) {
        try {
            cajaChicaBean = (CajaChicaBean) objeto;
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            cajaChicaBean = (CajaChicaBean) event.getObject();
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
//            if (cajaChicaBean.getFecCierre() == null) {
////                cajaChicaBean.setFecCierre(new Date());
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarCajaChica() {
        try {
            if (cajaChicaBean.getCreaFecha() == null) {
                insertarCajaChica();
            } else {
                modificarCajaChica();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCajaChicaBean() {
        cajaChicaBean = new CajaChicaBean();
        cajaChicaBean.setAnio(Calendar.getInstance().get((Calendar.YEAR)));
    }

    public void ponerFechaCierre() {
        if (cajaChicaBean.getFecCierre() == null) {
            cajaChicaBean.setFecCierre(new Date());
        }
    }

    //aPERTURA
    public void rowSelectCajaChicaAperutra(SelectEvent event) {
        try {
            cajaChicaBean = (CajaChicaBean) event.getObject();
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            List<CajaChicaMovBean> listaCajaChicaMov = new ArrayList<>();
            CajaChicaMovBean cajaChicaMovBean = new CajaChicaMovBean();
            cajaChicaMovBean.setCajaChicaBean(cajaChicaBean);
            listaCajaChicaMov = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMovBean);
            CajaChicaMovMB cajaChicaMovMB = (CajaChicaMovMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cajaChicaMovMB");
            cajaChicaMovMB.setCajaChicaBean(cajaChicaBean);
            cajaChicaMovMB.setCajaChicaMovBean(new CajaChicaMovBean());
            cajaChicaMovMB.setListaCajaChicaMovBean(listaCajaChicaMov);
//            cajaChicaMovMB.obtenerSaldo();
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cajaChicaMovMB", cajaChicaMovMB);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void imprimir() {
//        ServletOutputStream out = null;
//        try {
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
//                    getResponse();
//            String k = "<html><body> This is my Project </body></html>";
//            File file = new File("C:\\Test.pdf");
//            OutputStream os = new FileOutputStream(file);
//            Document document = new Document();
//            PdfWriter.getInstance(document, os);
//            document.open();
//            HTMLWorker htmlWorker = new HTMLWorker(document);
//            htmlWorker.parse(new StringReader(k));
//            document.close();
//            os.close();
//            byte[] bytes = Files.toByteArray(file);
//            file.delete();
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
//            } catch (Exception eww) {
//                new MensajePrime().addErrorGeneralMessage();
//                GLTLog.writeError(this.getClass(), eww);
//            }
//        }
//        FacesContext.getCurrentInstance().responseComplete();
//    }
//Getter  y Setter
    public CajaChicaBean getCajaChicaBean() {
        if (cajaChicaBean == null) {
            cajaChicaBean = new CajaChicaBean();
        }
        return cajaChicaBean;
    }

    public void setCajaChicaBean(CajaChicaBean cajaChicaBean) {
        this.cajaChicaBean = cajaChicaBean;
    }

    public List<CajaChicaBean> getListaCajaChicaBean() {
        if (listaCajaChicaBean == null) {
            listaCajaChicaBean = new ArrayList<>();
        }
        return listaCajaChicaBean;
    }

    public void setListaCajaChicaBean(List<CajaChicaBean> listaCajaChicaBean) {
        this.listaCajaChicaBean = listaCajaChicaBean;
    }

    public CajaChicaBean getCajaChicaFiltroBean() {
        if (cajaChicaFiltroBean == null) {
            cajaChicaFiltroBean = new CajaChicaBean();
        }
        return cajaChicaFiltroBean;
    }

    public void setCajaChicaFiltroBean(CajaChicaBean cajaChicaFiltroBean) {
        this.cajaChicaFiltroBean = cajaChicaFiltroBean;
    }

    public UsuarioBean getUsuarioLogin() {
        if (usuarioLogin == null) {
            usuarioLogin = new UsuarioBean();
        }
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioBean usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public List<VistaBean> getListaCajeroBean() {
        if (listaCajeroBean == null) {
            listaCajeroBean = new ArrayList<>();
        }
        return listaCajeroBean;
    }

    public void setListaCajeroBean(List<VistaBean> listaCajeroBean) {
        this.listaCajeroBean = listaCajeroBean;
    }

//    public List<TipoCambioBean> getListaTipoCambioBean() {
//        if (listaTipoCambioBean == null) {
//            listaTipoCambioBean = new ArrayList<>();
//        }
//        return listaTipoCambioBean;
//    }
//
//    public void setListaTipoCambioBean(List<TipoCambioBean> listaTipoCambioBean) {
//        this.listaTipoCambioBean = listaTipoCambioBean;
//    }
    public void expandir(Object objeto) {
        try {
            CajaChicaBean bean = (CajaChicaBean) objeto;
            System.out.println("id: " + bean.getIdCajaChica());
        } catch (Exception e) {
        }
    }

    public List<CodigoBean> getListaTipoCajaChica() {
        if (listaTipoCajaChica == null) {
            listaTipoCajaChica = new ArrayList<>();
        }
        return listaTipoCajaChica;
    }

    public void setListaTipoCajaChica(List<CodigoBean> listaTipoCajaChica) {
        this.listaTipoCajaChica = listaTipoCajaChica;
    }

}
