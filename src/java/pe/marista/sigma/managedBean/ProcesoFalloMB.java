package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ProcesoFalloRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class ProcesoFalloMB implements Serializable {
    
    @PostConstruct
    public void ProcesoFalloMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getProcesoFalloRepBean();
            getProcesoFalloRepBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoFalloRepBean().setFechaIni(fechaActual.getTime());
            getProcesoFalloRepBean().setFechaFin(fechaActual.getTime());
            Integer var = 0;
            procesoFalloRepBean.setTotalMonto(new BigDecimal(var.floatValue()));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    private ProcesoFalloRepBean procesoFalloRepBean;
    private List<ProcesoFalloRepBean> listaProcesoFalloRepBean;
    private UsuarioBean beanUsuarioSesion;

    //GET AND SET
    public ProcesoFalloRepBean getProcesoFalloRepBean() {
        if (procesoFalloRepBean == null) {
            procesoFalloRepBean = new ProcesoFalloRepBean();
        }
        return procesoFalloRepBean;
    }
    
    public void setProcesoFalloRepBean(ProcesoFalloRepBean procesoFalloRepBean) {
        this.procesoFalloRepBean = procesoFalloRepBean;
    }
    
    public List<ProcesoFalloRepBean> getListaProcesoFalloRepBean() {
        if (listaProcesoFalloRepBean == null) {
            listaProcesoFalloRepBean = new ArrayList<>();
        }
        return listaProcesoFalloRepBean;
    }
    
    public void setListaProcesoFalloRepBean(List<ProcesoFalloRepBean> listaProcesoFalloRepBean) {
        this.listaProcesoFalloRepBean = listaProcesoFalloRepBean;
    }
    
    public UsuarioBean getBeanUsuarioSesion() {
        if (beanUsuarioSesion == null) {
            beanUsuarioSesion = new UsuarioBean();
        }
        return beanUsuarioSesion;
    }
    
    public void setBeanUsuarioSesion(UsuarioBean beanUsuarioSesion) {
        this.beanUsuarioSesion = beanUsuarioSesion;
    }

    //END
    public void filtrarFallo() {
        try {
            Integer res = 0;
            if (procesoFalloRepBean.getFechaIni() != null
                    && !procesoFalloRepBean.getFechaIni().equals("")) {
                procesoFalloRepBean.setFechaIni(procesoFalloRepBean.getFechaIni());
                res = 1;
            }
            if (procesoFalloRepBean.getFechaFin() != null
                    && !procesoFalloRepBean.getFechaFin().equals("")) {
                procesoFalloRepBean.setFechaFin(procesoFalloRepBean.getFechaFin());
                res = 1;
            }
            if (procesoFalloRepBean.getCodigo() != null
                    && !procesoFalloRepBean.getCodigo().equals("")) {
                procesoFalloRepBean.setCodigo(procesoFalloRepBean.getCodigo());
                res = 1;
            }
            if (procesoFalloRepBean.getIdestudiante() != null
                    && !procesoFalloRepBean.getIdestudiante().equals("")) {
                procesoFalloRepBean.setIdestudiante(procesoFalloRepBean.getIdestudiante());
                res = 1;
            }
            if (procesoFalloRepBean.getNombres() != null
                    && !procesoFalloRepBean.getNombres().equals("")) {
                procesoFalloRepBean.setNombres(procesoFalloRepBean.getNombres());
                res = 1;
            }
            if (res == 1) {
                ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
                listaProcesoFalloRepBean = procesoFinalService.obtenerListaFallo(procesoFalloRepBean);
                procesoFalloRepBean.setTotalMonto(listaProcesoFalloRepBean.get(0).getTotalMonto());
                if (listaProcesoFalloRepBean.isEmpty()) {
                    new MensajePrime().addInformativeMessage("informacionEtiqueta");
                }
            } else {
                new MensajePrime().addInformativeMessage("addInformativeMessageFilterField");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void limpiarFallo() {
        try {
            procesoFalloRepBean = new ProcesoFalloRepBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
}
