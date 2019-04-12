/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.NivelTipoAccesoBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.NivelTipoAccesoService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS-001
 */
public class NivelTipoAccesoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of NivelTipoAccesoMB
     */
    public NivelTipoAccesoMB() {
    }

    @PostConstruct
    public void init() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//             

            cargarLista();

            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoAccessoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ACCESO));
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    private UsuarioBean usuarioLogin;

    private Integer periodo;
    private NivelTipoAccesoBean nivelTipoAccesoBean;
    private List<NivelTipoAccesoBean> listaNivelTipoAccessoBean;
    private List<CodigoBean> listaTipoAccessoBean;

    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad;
    private DualListModel<PlanContableBean> dualCuenta;

    //Metodos de aplicacion
    public void obtenerPorId(NivelTipoAccesoBean acceso) {
        try {
            NivelTipoAccesoService nivelTipoAccesoService = BeanFactory.getNivelTipoAccesoService();
            nivelTipoAccesoBean = nivelTipoAccesoService.obtenerNivelTipoAcceso(acceso);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String eliminar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {

                NivelTipoAccesoService nivelTipoAccesoService = BeanFactory.getNivelTipoAccesoService();
                nivelTipoAccesoService.eliminarNivelTipoAcceso(nivelTipoAccesoBean);

                limpiar();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiar() {
        try {
            nivelTipoAccesoBean = new NivelTipoAccesoBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoAccessoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ACCESO));
            cargarLista();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public final void cargarLista() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            periodo = miCalendario.get(Calendar.YEAR);
            getNivelTipoAccesoBean();
            getNivelTipoAccesoBean().setAnio(periodo);

            NivelTipoAccesoService nivelTipoAccesoService = BeanFactory.getNivelTipoAccesoService();
            NivelTipoAccesoBean bean = new NivelTipoAccesoBean();
            bean.setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            bean.setAnio(periodo);
            listaNivelTipoAccessoBean = new ArrayList<>();
            getListaNivelTipoAccessoBean();
            listaNivelTipoAccessoBean = nivelTipoAccesoService.obtenerNivelTipoAccesoPorAnio(bean);
            System.out.println("size " + listaNivelTipoAccessoBean.size());

            List<CentroResponsabilidadBean> listaCRDestino = new ArrayList<>();
            List<CentroResponsabilidadBean> listaCROrigen = new ArrayList<>();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCROrigen = centroResponsabilidadService.obtenerCentroResponsabilidad();

            PlanContableService planContableService = BeanFactory.getPlanContableService();
            List<PlanContableBean> listaCuentaOrigen = new ArrayList<>();
//            listaCuentaOrigen = planContableService.obtenerPlanContable();
            listaCuentaOrigen = planContableService.obtenerCuentaPorPresupuesto(periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            List<PlanContableBean> listaCuentaDestino = new ArrayList<>();

            dualCentroResponsabilidad = new DualListModel<>(listaCROrigen, listaCRDestino);
            dualCuenta = new DualListModel<>(listaCuentaOrigen, listaCuentaDestino);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarNivelTipoAcceso() {
        try {
            if (getNivelTipoAccesoBean().getTipoAccesoBean().getIdCodigo() != null) {
                insertarNivelTipoAcceso();
            } else {
                 new MensajePrime().addInformativeMessagePer("etiquetaTipAccesoReq");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }
    
    public String insertarNivelTipoAcceso() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                System.out.println("nivelTipoAccesoBean.getTipoAccesoBean().getIdCodigo()_"+getNivelTipoAccesoBean().getTipoAccesoBean().getIdCodigo());
                if (getNivelTipoAccesoBean().getTipoAccesoBean().getIdCodigo() != null) {
                    NivelTipoAccesoService nivelTipoAccesoService = BeanFactory.getNivelTipoAccesoService();
                    nivelTipoAccesoBean.setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    System.out.println("uniNeg..." + nivelTipoAccesoBean.getUniNeg());
                    nivelTipoAccesoBean.setAnio(periodo);
                    System.out.println("setAnio..." + nivelTipoAccesoBean.getAnio());

                    nivelTipoAccesoBean.setCreaPor(usuarioLogin.getUsuario());
                    nivelTipoAccesoService.insertarNivelTipoAccesoDual(nivelTipoAccesoBean, dualCentroResponsabilidad.getTarget(), dualCuenta.getTarget());
                    cargarLista();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }else {
                 new MensajePrime().addInformativeMessagePer("etiquetaTipAccesoReq");
                }

            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelect(SelectEvent event) {
        try {
            nivelTipoAccesoBean = (NivelTipoAccesoBean) event.getObject();

            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            List<CentroResponsabilidadBean> listaCRDestino = new ArrayList<>();
            List<CentroResponsabilidadBean> listaCROrigen = new ArrayList<>();
            listaCROrigen = centroResponsabilidadService.obtenerOutCrAcceso(nivelTipoAccesoBean.getTipoAccesoBean().getIdCodigo(), periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCRDestino = centroResponsabilidadService.obtenerInCrAcceso(nivelTipoAccesoBean.getTipoAccesoBean().getIdCodigo(), periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            PlanContableService planContableService = BeanFactory.getPlanContableService();
            List<PlanContableBean> listaCuentaOrigen = new ArrayList<>();
            List<PlanContableBean> listaCuentaDestino = new ArrayList<>();
            listaCuentaOrigen = planContableService.obtenerOutCuentaAcceso(nivelTipoAccesoBean.getTipoAccesoBean().getIdCodigo(), periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCuentaDestino = planContableService.obtenerInCuentaAcceso(nivelTipoAccesoBean.getTipoAccesoBean().getIdCodigo(), periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            dualCentroResponsabilidad = new DualListModel<>(listaCROrigen, listaCRDestino);
            dualCuenta = new DualListModel<>(listaCuentaOrigen, listaCuentaDestino);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //////////////////////////////////////////////
    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public NivelTipoAccesoBean getNivelTipoAccesoBean() {
        if (nivelTipoAccesoBean == null) {
            nivelTipoAccesoBean = new NivelTipoAccesoBean();
        }
        return nivelTipoAccesoBean;
    }

    public void setNivelTipoAccesoBean(NivelTipoAccesoBean nivelTipoAccesoBean) {
        this.nivelTipoAccesoBean = nivelTipoAccesoBean;
    }

    public List<NivelTipoAccesoBean> getListaNivelTipoAccessoBean() {
        if (listaNivelTipoAccessoBean == null) {
            listaNivelTipoAccessoBean = new ArrayList<>();
        }
        return listaNivelTipoAccessoBean;
    }

    public void setListaNivelTipoAccessoBean(List<NivelTipoAccesoBean> listaNivelTipoAccessoBean) {
        this.listaNivelTipoAccessoBean = listaNivelTipoAccessoBean;
    }

    public DualListModel<CentroResponsabilidadBean> getDualCentroResponsabilidad() {
        return dualCentroResponsabilidad;
    }

    public void setDualCentroResponsabilidad(DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad) {
        this.dualCentroResponsabilidad = dualCentroResponsabilidad;
    }

    public List<CodigoBean> getListaTipoAccessoBean() {
        if (listaTipoAccessoBean == null) {
            listaTipoAccessoBean = new ArrayList<>();
        }
        return listaTipoAccessoBean;
    }

    public void setListaTipoAccessoBean(List<CodigoBean> listaTipoAccessoBean) {
        this.listaTipoAccessoBean = listaTipoAccessoBean;
    }

    public DualListModel<PlanContableBean> getDualCuenta() {
        return dualCuenta;
    }

    public void setDualCuenta(DualListModel<PlanContableBean> dualCuenta) {
        this.dualCuenta = dualCuenta;
    }

}
