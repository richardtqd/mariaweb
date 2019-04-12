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
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class CuentaBancoMB implements Serializable {

    /**
     * Creates a new instance of CuentaBancoMB
     */
    @PostConstruct
    public void init() {
        try {
            //sesión del usuario
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaViewEntidadBancoBean();
            listaViewEntidadBancoBean = entidadService.obtenerEntidadPorVistaPorUniNeg(MaristaConstantes.VIEW_ENT_FIN, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoMonedaBean();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON)); 

            getListaTipoCuentaBean();
            listaTipoCuentaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CUENTA_BCO));
//            listaTipoCuentaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CUENTA));
//            CodigoBean codigo = new CodigoBean();
//            codigo=codigoService.obtenerPorCodigo(new CodigoBean(0,"Activo",new TipoCodigoBean(MaristaConstantes.TIP_CUENTA)));
//            getCuentaBancoBean().getTipoCuentaBancoBean().setIdCodigo(codigo.getIdCodigo());
            setMostrarPanel(true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private CuentaBancoBean cuentaBancoBean;
    private CuentaBancoBean cuentaBancoVista;
    private CuentaBancoBean cuentaBancoFiltroBean;
    private List<CuentaBancoBean> listaCuentaBancoBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaTipoCuentaBean;
    private List<ViewEntidadBean> listaViewEntidadBancoBean;
    private UsuarioBean usuarioLoginBean;
    private Boolean mostrarPanel;
    private List<PlanContableBean> listaPlanContableBanco;

    public void limpiarCuentaBanco() {
        cuentaBancoBean = new CuentaBancoBean();
        mostrarPanel = false;
    }

    public void obtenerCuentaBancoPorUniNeg() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            cuentaBancoBean = new CuentaBancoBean();
            cuentaBancoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            listaCuentaBancoBean = cuentaBancoService.obtenerCuentaPorUniNeg(cuentaBancoBean.getUnidadNegocioBean().getUniNeg());
            limpiarCuentaBanco();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaBancoPorPlanContable() {
        try {
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            PlanContableBean planContableBean = new PlanContableBean();
            cuentaBancoBean.setCtaContBco(null);
            if (cuentaBancoBean.getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                planContableBean.setCuenta(MaristaConstantes.CTA_BCO_SOLES);
                listaPlanContableBanco = planContableService.obtenerPlanContableFiltro(planContableBean);
            } else {
                planContableBean.setCuenta(MaristaConstantes.CTA_BCO_DOL);
                listaPlanContableBanco = planContableService.obtenerPlanContableFiltro(planContableBean);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCuentaBanco() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
                cuentaBancoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                cuentaBancoBean.setCreaPor(usuarioLoginBean.getUsuario());
                cuentaBancoService.insertarCuentaBanco(cuentaBancoBean);
                listaCuentaBancoBean = cuentaBancoService.obtenerCuentaPorUniNeg(cuentaBancoBean.getUnidadNegocioBean().getUniNeg());
                this.mostrarPanel = true;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCuentaBanco() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
                cuentaBancoBean.setModiPor(usuarioLoginBean.getUsuario());
                cuentaBancoService.modificarCuentaBanco(cuentaBancoBean);
                listaCuentaBancoBean = cuentaBancoService.obtenerCuentaPorUniNeg(cuentaBancoBean.getUnidadNegocioBean().getUniNeg());
                this.mostrarPanel = true;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarCuentaBanco() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
                cuentaBancoService.eliminarCuentaBanco(cuentaBancoBean);
                listaCuentaBancoBean = cuentaBancoService.obtenerCuentaPorUniNeg(cuentaBancoBean.getUnidadNegocioBean().getUniNeg());
                this.mostrarPanel = true;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarCuentaBanco() {
        if (cuentaBancoBean.getNumCuenta() != null) {
            modificarCuentaBanco();
        } else {
            insertarCuentaBanco();
        }
    }
    
    public void obtenerCuentaBancoPorId(Object object) {
        try {
            cuentaBancoBean = (CuentaBancoBean) object;
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            cuentaBancoBean = cuentaBancoService.obtenerCuentaBancoPorId(cuentaBancoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void comprobarCuentaBanco() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            cuentaBancoVista = cuentaBancoService.obtenerCuentaBancoPorId(cuentaBancoBean);
            if (cuentaBancoVista != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else {
                insertarCuentaBanco();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            cuentaBancoBean = (CuentaBancoBean) event.getObject();
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            cuentaBancoBean = cuentaBancoService.obtenerCuentaBancoPorId(cuentaBancoBean);
            this.mostrarPanel = false;
            obtenerPlanContable();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPlanContable() {
        try {
            if (listaPlanContableBanco.isEmpty()) {
                PlanContableService planContableService = BeanFactory.getPlanContableService();
                PlanContableBean planContable = new PlanContableBean();
                planContable.setCuenta(MaristaConstantes.CTA_BCO_SOLES_DOL); 
                listaPlanContableBanco = planContableService.obtenerPlanContableFiltro(planContable);
            }
                for (int i = 0; i < listaPlanContableBanco.size(); i++) {
                    if (cuentaBancoBean.getCtaContBco() != null
                            && listaPlanContableBanco.get(i).getCuenta().equals(cuentaBancoBean.getCtaContBco())) {
                        cuentaBancoBean.setCtaContBco(listaPlanContableBanco.get(i).getCuenta());
                        break;
                    }
                }
            }catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        }
        //metodos getter and setter
    public CuentaBancoBean getCuentaBancoBean() {
        if (cuentaBancoBean == null) {
            cuentaBancoBean = new CuentaBancoBean();
        }
        return cuentaBancoBean;
    }

    public void setCuentaBancoBean(CuentaBancoBean cuentaBancoBean) {
        this.cuentaBancoBean = cuentaBancoBean;
    }

    public CuentaBancoBean getCuentaBancoFiltroBean() {
        if (cuentaBancoFiltroBean == null) {
            cuentaBancoFiltroBean = new CuentaBancoBean();
        }
        return cuentaBancoFiltroBean;
    }

    public void setCuentaBancoFiltroBean(CuentaBancoBean cuentaBancoFiltroBean) {
        this.cuentaBancoFiltroBean = cuentaBancoFiltroBean;
    }

    public List<CuentaBancoBean> getListaCuentaBancoBean() {
        if (listaCuentaBancoBean == null) {
            listaCuentaBancoBean = new ArrayList();
        }
        return listaCuentaBancoBean;
    }

    public void setListaCuentaBancoBean(List<CuentaBancoBean> listaCuentaBancoBean) {
        this.listaCuentaBancoBean = listaCuentaBancoBean;
    }

    public List<CodigoBean> getListaTipoMonedaBean() {
        if (listaTipoMonedaBean == null) {
            listaTipoMonedaBean = new ArrayList();
        }
        return listaTipoMonedaBean;
    }

    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
        this.listaTipoMonedaBean = listaTipoMonedaBean;
    }

    public List<CodigoBean> getListaTipoCuentaBean() {
        if (listaTipoCuentaBean == null) {
            listaTipoCuentaBean = new ArrayList();
        }
        return listaTipoCuentaBean;
    }

    public void setListaTipoCuentaBean(List<CodigoBean> listaTipoCuentaBean) {
        this.listaTipoCuentaBean = listaTipoCuentaBean;
    }

    public List<ViewEntidadBean> getListaViewEntidadBancoBean() {
        if (listaViewEntidadBancoBean == null) {
            listaViewEntidadBancoBean = new ArrayList();
        }
        return listaViewEntidadBancoBean;
    }

    public void setListaViewEntidadBancoBean(List<ViewEntidadBean> listaViewEntidadBancoBean) {
        this.listaViewEntidadBancoBean = listaViewEntidadBancoBean;
    }

    public CuentaBancoBean getCuentaBancoVista() {
        if (cuentaBancoVista == null) {
            cuentaBancoVista = new CuentaBancoBean();
        }
        return cuentaBancoVista;
    }

    public void setCuentaBancoVista(CuentaBancoBean cuentaBancoVista) {
        this.cuentaBancoVista = cuentaBancoVista;
    }

    public Boolean getMostrarPanel() {
        return mostrarPanel;
    }

    public void setMostrarPanel(Boolean mostrarPanel) {
        this.mostrarPanel = mostrarPanel;
    }

    public List<PlanContableBean> getListaPlanContableBanco() {
        if (listaPlanContableBanco == null) {
            listaPlanContableBanco = new ArrayList<>();
        }
        return listaPlanContableBanco;
    }

    public void setListaPlanContableBanco(List<PlanContableBean> listaPlanContableBanco) {
        this.listaPlanContableBanco = listaPlanContableBanco;
    }

}
