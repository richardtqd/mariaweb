package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ConciliaBancoRepBean;
import pe.marista.sigma.bean.reporte.ConciliaRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class ConciliaBancoMB implements Serializable {

    @PostConstruct
    public void ConciliaBancoMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getConciliaRepBean();
            getConciliaRepBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            GregorianCalendar fecha = new GregorianCalendar();
            getConciliaRepBean().setFecIni(fecha.getTime());
            getConciliaRepBean().setFecFin(fecha.getTime());
            getConciliaRepBean().setFlgProceso(0);
            Integer res = 0;
            totSoles = new BigDecimal(res.floatValue());
            totSolesPro = new BigDecimal(res.floatValue());
            getConciliaRepBean();
            getConciliaRepBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private UsuarioBean usuarioLoginBean;

    private GregorianCalendar fechaActual;
    private BigDecimal totSoles;
    private BigDecimal totSolesPro;

    private ConciliaBancoRepBean conciliaBancoRepBean;
    private List<ConciliaBancoRepBean> listaConciliaBancoRepBean;
    private List<ConciliaBancoRepBean> listaConciliaBancoRepBeanFallo;

    private ConciliaRepBean conciliaRepBean;
    private List<ConciliaRepBean> listaConciliaRepBean;
    private Boolean disabled;
    private Boolean disabledFail;

    /* GET Y SET */
    public ConciliaBancoRepBean getConciliaBancoRepBean() {
        if (conciliaBancoRepBean == null) {
            conciliaBancoRepBean = new ConciliaBancoRepBean();
        }
        return conciliaBancoRepBean;
    }

    public void setConciliaBancoRepBean(ConciliaBancoRepBean conciliaBancoRepBean) {
        this.conciliaBancoRepBean = conciliaBancoRepBean;
    }

    public List<ConciliaBancoRepBean> getListaConciliaBancoRepBean() {
        if (listaConciliaBancoRepBean == null) {
            listaConciliaBancoRepBean = new ArrayList<>();
        }
        return listaConciliaBancoRepBean;
    }

    public void setListaConciliaBancoRepBean(List<ConciliaBancoRepBean> listaConciliaBancoRepBean) {
        this.listaConciliaBancoRepBean = listaConciliaBancoRepBean;
    }

    public GregorianCalendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(GregorianCalendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public ConciliaRepBean getConciliaRepBean() {
        if (conciliaRepBean == null) {
            conciliaRepBean = new ConciliaRepBean();
        }
        return conciliaRepBean;
    }

    public void setConciliaRepBean(ConciliaRepBean conciliaRepBean) {
        this.conciliaRepBean = conciliaRepBean;
    }

    public List<ConciliaRepBean> getListaConciliaRepBean() {
        if (listaConciliaRepBean == null) {
            listaConciliaRepBean = new ArrayList<>();
        }
        return listaConciliaRepBean;
    }

    public void setListaConciliaRepBean(List<ConciliaRepBean> listaConciliaRepBean) {
        this.listaConciliaRepBean = listaConciliaRepBean;
    }

    public BigDecimal getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(BigDecimal totSoles) {
        this.totSoles = totSoles;
    }

    public BigDecimal getTotSolesPro() {
        return totSolesPro;
    }

    public void setTotSolesPro(BigDecimal totSolesPro) {
        this.totSolesPro = totSolesPro;
    }

    public UsuarioBean getUsuarioLoginBean() {
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public List<ConciliaBancoRepBean> getListaConciliaBancoRepBeanFallo() {
        if (listaConciliaBancoRepBeanFallo == null) {
            listaConciliaBancoRepBeanFallo = new ArrayList<>();
        }
        return listaConciliaBancoRepBeanFallo;
    }

    public void setListaConciliaBancoRepBeanFallo(List<ConciliaBancoRepBean> listaConciliaBancoRepBeanFallo) {
        this.listaConciliaBancoRepBeanFallo = listaConciliaBancoRepBeanFallo;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDisabledFail() {
        return disabledFail;
    }

    public void setDisabledFail(Boolean disabledFail) {
        this.disabledFail = disabledFail;
    }

    /* ======== */
    /* METODOS */
    public void filtrarBanco() {
        try {
            Integer res = 0;
            Float pro = res.floatValue(), rec = res.floatValue();
            if (getConciliaRepBean().getFecIni() != null) {
                getConciliaRepBean().setFecIni(getConciliaRepBean().getFecIni());
            }
            if (getConciliaRepBean().getFecFin() != null) {
                getConciliaRepBean().setFecFin(getConciliaRepBean().getFecFin());
            }
            if (getConciliaRepBean().getFlgProceso() != null) {
                getConciliaRepBean().setFlgProceso(getConciliaRepBean().getFlgProceso());
            }
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaConciliaRepBean = procesoBancoService.obtenerBancoConcilia(getConciliaRepBean());
            if (listaConciliaRepBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                totSoles = new BigDecimal(pro.floatValue());
                totSolesPro = new BigDecimal(rec.floatValue());
            } else {
                if (!listaConciliaRepBean.isEmpty()) {
                    for (ConciliaRepBean concilia : listaConciliaRepBean) {
                        pro = concilia.getSumProcesado().floatValue() + pro;
                        rec = concilia.getSumRecuperado().floatValue() + rec;
                    }
                    totSoles = new BigDecimal(pro.floatValue());
                    totSolesPro = new BigDecimal(rec.floatValue());
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarconcilia() {
        try {
            conciliaRepBean = new ConciliaRepBean();
            Integer res = 0;
            totSoles = new BigDecimal(res.floatValue());
            totSolesPro = new BigDecimal(res.floatValue());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerRepBanco(Object obj, Integer param) {
        try {
            conciliaRepBean = (ConciliaRepBean) obj;
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            conciliaBancoRepBean = new ConciliaBancoRepBean();
            conciliaBancoRepBean.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conciliaBancoRepBean.setIdProcesoBanco(conciliaRepBean.getIdProcesoBanco());
            if (param.equals(1)) {
                listaConciliaBancoRepBean = procesoFinalService.obtenerListaDetConcilia(conciliaBancoRepBean);
                if (listaConciliaBancoRepBean.isEmpty()) {
                    setDisabled(true);
                } else {
                    setDisabled(false);
                }
            } else if (param.equals(2)) {
                listaConciliaBancoRepBeanFallo = procesoFinalService.obtenerListaDetConciliaFallo(conciliaBancoRepBean);
                if (listaConciliaBancoRepBeanFallo.isEmpty()) {
                    setDisabledFail(true);
                } else {
                    setDisabledFail(false);
                }
            } else if (param.equals(3)) {
                getConciliaRepBean();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarProcesoBanco() {
        try {
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            procesoBancoService.eliminarProcesoBancoMas(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), conciliaRepBean.getIdProcesoBanco());
            filtrarBanco();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void actualizarRegistroBanco(Object object) {
        try {
            conciliaRepBean = (ConciliaRepBean) object;
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Object obj_genera = procesoFinalService.execProCtaCte1(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), conciliaRepBean.getRucBanco(), conciliaRepBean.getIdProcesoBanco(), usuarioLoginBean.getUsuario());
            Object obj_update = procesoFinalService.execProCtaCte2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), conciliaRepBean.getRucBanco(), conciliaRepBean.getIdProcesoBanco(), usuarioLoginBean.getUsuario());
            System.out.println(">>>>>" + obj_genera);
            System.out.println(">>>>>" + obj_update);
            filtrarBanco();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

}
