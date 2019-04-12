package pe.marista.sigma.managedBean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.PresupuestoUniOrgBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.service.PresupuestoUniOrgService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class PresupuestoPlanMB {

    @PostConstruct
    public void PresupuestoPlanMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getSelFiltro();
            setSelFiltro(1);
            obtenerFiltro();
            obtenerPresupuestos();
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            getListaPlanContableBean();
            listaPlanContableBean = planContableService.obtenerPlanContable();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //VARIABLES DE SESIÓN
    private UsuarioBean usuarioBean;

    //CLASES
    private PresupuestoBean presupuestoBean;
    private PresupuestoUniOrgBean presupuestoUniOrgBean;

    //LISTAS
    private List<PresupuestoBean> listaPresupuestoBean;
    private List<PresupuestoUniOrgBean> listaPresupuestoUniOrgBean;
    private List<PlanContableBean> listaPlanContableBean;

    //VARIABLES DE AYUDA
    private Integer selFiltro;
    private Boolean flgPres;
    private Boolean flgPresUo;

    public void obtenerFiltro() {
        try {
            getSelFiltro();
            if (getSelFiltro() != null) {
                if (getSelFiltro().equals(1)) {
                    flgPres = true;
                    flgPresUo = false;
                } else if (getSelFiltro().equals(2)) {
                    flgPresUo = true;
                    flgPres = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPresupuestos() {
        try {
            //SETEANDO PRESUPUESTO
            getPresupuestoBean();
            getPresupuestoBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPresupuestoBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            getPresupuestoBean().setCreaPor(usuarioBean.getUsuario());

            //SETEANDO PRESUPUESTO-OU
            getPresupuestoUniOrgBean();
            getPresupuestoUniOrgBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPresupuestoUniOrgBean().setCreaPor(usuarioBean.getUsuario());

            //OBTENIENDO LISTAS
            getListaPresupuestoBean();
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            listaPresupuestoBean = presupuestoService.obtenerPresupesto(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaPresupuestoUniOrgBean();
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            listaPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPresupuestoUniOrg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void execPresupuesto() {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuestoService.execPresupuesto(getPresupuestoBean());
            obtenerPresupuestos();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void buscarPresupuesto() {
        try {
            Integer res = 0;
            presupuestoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuestoBean.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println(">>>" + presupuestoBean.getUnidadNegocioBean().getUniNeg() + presupuestoBean.getUniNeg());
            if (presupuestoBean.getPlanContableBean().getCuenta() != null) {
                presupuestoBean.getPlanContableBean().setCuenta(presupuestoBean.getPlanContableBean().getCuenta());
                res = 1;
            }
            if (presupuestoBean.getAnio() != null) {
                presupuestoBean.setAnio(presupuestoBean.getAnio());
                res = 1;
            }
            if (res == 1) {
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
                listaPresupuestoBean = presupuestoService.filtrarPresupuesto(presupuestoBean);
                listaPresupuestoUniOrgBean = presupuestoUniOrgService.filtrarPresupuesto(presupuestoBean);
                if (listaPresupuestoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaPresupuestoBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPresupuestoBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
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

    public PresupuestoBean getPresupuestoBean() {
        if (presupuestoBean == null) {
            presupuestoBean = new PresupuestoBean();
        }
        return presupuestoBean;
    }

    public void setPresupuestoBean(PresupuestoBean presupuestoBean) {
        this.presupuestoBean = presupuestoBean;
    }

    public PresupuestoUniOrgBean getPresupuestoUniOrgBean() {
        if (presupuestoUniOrgBean == null) {
            presupuestoUniOrgBean = new PresupuestoUniOrgBean();
        }
        return presupuestoUniOrgBean;
    }

    public void setPresupuestoUniOrgBean(PresupuestoUniOrgBean presupuestoUniOrgBean) {
        this.presupuestoUniOrgBean = presupuestoUniOrgBean;
    }

    public List<PresupuestoBean> getListaPresupuestoBean() {
        if (listaPresupuestoBean == null) {
            listaPresupuestoBean = new ArrayList<>();
        }
        return listaPresupuestoBean;
    }

    public void setListaPresupuestoBean(List<PresupuestoBean> listaPresupuestoBean) {
        this.listaPresupuestoBean = listaPresupuestoBean;
    }

    public List<PresupuestoUniOrgBean> getListaPresupuestoUniOrgBean() {
        if (listaPresupuestoUniOrgBean == null) {
            listaPresupuestoUniOrgBean = new ArrayList<>();
        }
        return listaPresupuestoUniOrgBean;
    }

    public void setListaPresupuestoUniOrgBean(List<PresupuestoUniOrgBean> listaPresupuestoUniOrgBean) {
        this.listaPresupuestoUniOrgBean = listaPresupuestoUniOrgBean;
    }

    public Integer getSelFiltro() {
        return selFiltro;
    }

    public void setSelFiltro(Integer selFiltro) {
        this.selFiltro = selFiltro;
    }

    public Boolean getFlgPres() {
        return flgPres;
    }

    public void setFlgPres(Boolean flgPres) {
        this.flgPres = flgPres;
    }

    public Boolean getFlgPresUo() {
        return flgPresUo;
    }

    public void setFlgPresUo(Boolean flgPresUo) {
        this.flgPresUo = flgPresUo;
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
