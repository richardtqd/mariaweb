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
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.ProcesoFileBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.ProcesoFileService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class ProcesoFileMB implements Serializable {

    /**
     * Creates a new instance of ProcesoFileMB
     */
    @PostConstruct
    public void ProcesoFileMB() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaEntidadBean();
            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            obtenerProcesoFile();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private ProcesoFileBean procesoFileBean;
    private List<ProcesoFileBean> listaProcesoFileBean;
    private List<ProcesoFileBean> listaProcesoFile;
    private ProcesoFileBean procesoFileFiltro;
    private ProcesoFileBean procesoFile;

    //Ayuda
    private List<EntidadBean> listaEntidadBean;
    private Integer var1 = 1;
    private Integer var2 = 2;
    private Integer var3 = 3;

    private UsuarioBean usuarioSessionBean;

    public void obtenerProcesoFile() {
        try {
            ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
            listaProcesoFileBean = procesoFileService.obtenerProcesoFile();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarProcesoFile() {
        try {
            procesoFileBean = new ProcesoFileBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectFile(SelectEvent event) {
        try {
            procesoFileBean = (ProcesoFileBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarProcesoFile() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
                procesoFile.setCreaPor(beanUsuarioSesion.getUsuario());
                procesoFileService.insertarProcesoFile(procesoFile);
                listaProcesoFileBean = procesoFileService.obtenerProcesoFile();
                limpiarProcesoFile();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarProcesoFile() {
        String pagina = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
                procesoFile.setModiPor(beanUsuarioSesion.getUsuario());
                procesoFileService.modificarProcesoFile(procesoFile);
                listaProcesoFileBean = procesoFileService.obtenerProcesoFile();
                limpiarProcesoFile();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardar() {
        try {
            if (procesoFile.getEntidadBean().getRuc() != null) {
                modificarProcesoFile();
            } else {
                insertarProcesoFile();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public ProcesoFileBean getProcesoFileBean() {
        if (procesoFileBean == null) {
            procesoFileBean = new ProcesoFileBean();
        }
        return procesoFileBean;
    }

    public void setProcesoFileBean(ProcesoFileBean procesoFileBean) {
        this.procesoFileBean = procesoFileBean;
    }

    public List<ProcesoFileBean> getListaProcesoFileBean() {
        if (listaProcesoFileBean == null) {
            listaProcesoFileBean = new ArrayList<>();
        }
        return listaProcesoFileBean;
    }

    public void setListaProcesoFileBean(List<ProcesoFileBean> listaProcesoFileBean) {
        this.listaProcesoFileBean = listaProcesoFileBean;
    }

    public ProcesoFileBean getProcesoFileFiltro() {
        if (procesoFileFiltro == null) {
            procesoFileFiltro = new ProcesoFileBean();
        }
        return procesoFileFiltro;
    }

    public void setProcesoFileFiltro(ProcesoFileBean procesoFileFiltro) {
        this.procesoFileFiltro = procesoFileFiltro;
    }

    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
    }

    public Integer getVar1() {
        return var1;
    }

    public void setVar1(Integer var1) {
        this.var1 = var1;
    }

    public Integer getVar2() {
        return var2;
    }

    public void setVar2(Integer var2) {
        this.var2 = var2;
    }

    public Integer getVar3() {
        return var3;
    }

    public void setVar3(Integer var3) {
        this.var3 = var3;
    }

    public ProcesoFileBean getProcesoFile() {
        if (procesoFile == null) {
            procesoFile = new ProcesoFileBean();
        }
        return procesoFile;
    }

    public void setProcesoFile(ProcesoFileBean procesoFile) {
        this.procesoFile = procesoFile;
    }

    public List<ProcesoFileBean> getListaProcesoFile() {
        if (listaProcesoFile == null) {
            listaProcesoFile = new ArrayList<>();
        }
        return listaProcesoFile;
    }

    public void setListaProcesoFile(List<ProcesoFileBean> listaProcesoFile) {
        this.listaProcesoFile = listaProcesoFile;
    }

}
