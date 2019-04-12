package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class TipoConceptoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of TipoConceptoMB
     */
    @PostConstruct
    public void TipoConceptoMB() {
        try {
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
            listaPlanContableBean = planContableService.obtenerPlanContable();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private TipoConceptoBean tipoConceptoBean;
    private ConceptoBean conceptoBean;
    private ConceptoUniNegBean conceptoUniNegBean;
    private List<ConceptoBean> listaConceptoBean;
    private List<ConceptoUniNegBean> listaConceptUniNegBean;
    private List<PlanContableBean> listaPlanContableBean;

    //Metodos Logica
    public void limpiarTipoConceptoBean() {
        tipoConceptoBean = new TipoConceptoBean();
        listaConceptoBean = new ArrayList<>();
        listaConceptUniNegBean = new ArrayList<>();
    }

    public String insertarTipoConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                tipoConceptoService.insertarTipoConcepto(tipoConceptoBean);
                listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
                limpiarTipoConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarTipoConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                tipoConceptoService.modificarTipoConcepto(tipoConceptoBean);
                listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
                limpiarTipoConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarTipoConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                tipoConceptoService.eliminarTipoConcepto(tipoConceptoBean.getIdTipoConcepto());
                listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
                limpiarTipoConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarTipoConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (tipoConceptoBean.getIdTipoConcepto() != null) {
                    modificarTipoConcepto();
                } else {
                    insertarTipoConcepto();
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerTipoConcepto() {
        try {
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object object) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            tipoConceptoBean = (TipoConceptoBean) object;

            tipoConceptoBean = tipoConceptoService.obtenerTipoConceptoPorId(tipoConceptoBean.getIdTipoConcepto());

            listaConceptUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

//            listaConceptoBean = conceptoService.obtenerPorTipo(tipoConceptoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarConceptoBean() {
        conceptoBean = new ConceptoBean();
        conceptoBean.setTipoConceptoBean(tipoConceptoBean);
    }

    public String insertarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                conceptoBean.getTipoConceptoBean().setIdTipoConcepto(tipoConceptoBean.getIdTipoConcepto());
                conceptoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                conceptoService.insertarConceptoUniNeg(conceptoBean, beanUsuarioSesion);
                listaConceptUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                listaConceptoBean = conceptoService.obtenerPorTipo(tipoConceptoBean);
                limpiarConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoService.modificarConcepto(conceptoBean);
//                listaConceptoBean = conceptoService.obtenerPorTipo(tipoConceptoBean);
                listaConceptUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoService.eliminarConcepto(conceptoBean, beanUsuarioSesion);
                listaConceptUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarConceptoBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerConceptoPorId(Object object) {
        try {
            conceptoBean = (ConceptoBean) object;
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            conceptoBean = conceptoService.obtenerConceptoPorId(conceptoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String guardarConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (conceptoBean.getPlanContableCuentaDBean().getCuenta() != null
                        && conceptoBean.getPlanContableCuentaHBean().getCuenta() != null
                        && !conceptoBean.getNombre().equals("")
                        && conceptoBean.getNombre() != null) {
                    if (conceptoBean.getIdConcepto() != null) {
                        modificarConcepto();
                    } else {
                        insertarConcepto();
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("Debe ingresar las cuentas contables");
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerConcepto(Object bean) {
        try {
            conceptoBean = (ConceptoBean) bean;
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            conceptoBean = conceptoService.obtenerConceptoPorId(conceptoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaContable() {
        try {
            System.out.println("cuenta: " + conceptoBean.getPlanContableCuentaDBean().getCuenta());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerTipoConcepto(Object tipo) {
        try {
            tipoConceptoBean = (TipoConceptoBean) tipo;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            tipoConceptoBean = (TipoConceptoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Metodos Getter y Setter
    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean == null) {
            tipoConceptoBean = new TipoConceptoBean();
        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
    }

    public List<ConceptoBean> getListaConceptoBean() {
        if (listaConceptoBean == null) {
            listaConceptoBean = new ArrayList<>();
        }
        return listaConceptoBean;
    }

    public void setListaConceptoBean(List<ConceptoBean> listaConceptoBean) {
        this.listaConceptoBean = listaConceptoBean;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public List<ConceptoUniNegBean> getListaConceptUniNegBean() {
        if (listaConceptUniNegBean == null) {
            listaConceptUniNegBean = new ArrayList<>();
        }

        return listaConceptUniNegBean;
    }

    public void setListaConceptUniNegBean(List<ConceptoUniNegBean> listaConceptUniNegBean) {
        this.listaConceptUniNegBean = listaConceptUniNegBean;
    }

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public List<PlanContableBean> getListaPlanContableBean() {
        if (listaPlanContableBean == null) {
            listaPlanContableBean = new ArrayList<>();
        }
        return listaPlanContableBean;
    }

    public void setListaPlanContableBean(List<PlanContableBean> listaPlanContableBean) {
        this.listaPlanContableBean = listaPlanContableBean;
    }

}
