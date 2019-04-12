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
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class CentroResponsabilidadMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CentroResponsabilidadMB
     */
    @PostConstruct
    public void CentroResponsabilidadMB() {
        try {
            obtenerCRNivel3();
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCentroTipoGrupoCR();
            listaCentroTipoGrupoCR = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_GRUPOCR));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private CodigoBean codigoBean;
    private List<CodigoBean> listaCodigoBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidad;
    private List<CentroResponsabilidadBean> listaCRFiltro;
    private List<CentroResponsabilidadBean> listaCentroResNivel3;
    private List<CentroResponsabilidadBean> listaCentroResNombre;
    private List<CodigoBean> listaCentroTipoGrupoCR;
    private Integer idTipoGrupoCR;

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidad() {
        if (listaCentroResponsabilidad == null) {
            listaCentroResponsabilidad = new ArrayList<>();
        }
        return listaCentroResponsabilidad;
    }

    public void setListaCentroResponsabilidad(List<CentroResponsabilidadBean> listaCentroResponsabilidad) {
        this.listaCentroResponsabilidad = listaCentroResponsabilidad;
    }

    public void obtenerTodos() {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentroResponsabilidad = centroResponsabilidadService.obtenerCentroResponsabilidad();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCRNivel3() {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentroResNivel3 = centroResponsabilidadService.obtenerCentroResNivel3();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

  

    public void RowSelect(SelectEvent event) {
        try {
            centroResponsabilidadBean = (CentroResponsabilidadBean) event.getObject();
            
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCentroResposabilidad() {
        centroResponsabilidadBean = new CentroResponsabilidadBean();
    }

    //Cambio
    public void obtenerCentroResPorNombre(String nombre) {
        try {

//            centroResponsabilidadBean = (CentroResponsabilidadBean) object;
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentroResNombre = (List<CentroResponsabilidadBean>) centroResponsabilidadService.obtenerCentroResPorNombre(nombre);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public List<CentroResponsabilidadBean> getListaCentroResNivel3() {
        if (listaCentroResNivel3 == null) {
            listaCentroResNivel3 = new ArrayList<>();
        }
        return listaCentroResNivel3;
    }

    public String insertarCentroResponsabilidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                centroResponsabilidadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                centroResponsabilidadService.insertarCentroResponsabilidad(centroResponsabilidadBean);
                listaCentroResponsabilidad = centroResponsabilidadService.obtenerCentroResponsabilidad();
                limpiarCentroResposabilidad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCentroResponsabilidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                centroResponsabilidadService.modificarCentroResponsabilidad(centroResponsabilidadBean);
                listaCentroResponsabilidad = centroResponsabilidadService.obtenerCentroResponsabilidad();
                limpiarCentroResposabilidad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarCentroResposabilidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                centroResponsabilidadService.eliminarCentroResposabilidad(centroResponsabilidadBean.getCr());
                listaCentroResponsabilidad = centroResponsabilidadService.obtenerCentroResponsabilidad();
                limpiarCentroResposabilidad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarCentroResposabilidad() {
        try {
            if (centroResponsabilidadBean.getCr() == null) {
                insertarCentroResponsabilidad();
            } else {
                modificarCentroResponsabilidad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    public void obtenerIdTipoCR() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentroResponsabilidad = centroResponsabilidadService.obtenerIdTipoCR(centroResponsabilidadBean.getTipoGrupoCRBean().getIdCodigo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    

    public void setListaCentroResNivel3(List<CentroResponsabilidadBean> listaCentroResNivel3) {
        this.listaCentroResNivel3 = listaCentroResNivel3;
    }

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public List<CodigoBean> getListaCodigoBean() {
        if (listaCodigoBean == null) {
            listaCodigoBean = new ArrayList<>();
        }
        return listaCodigoBean;
    }

    public void setListaCodigoBean(List<CodigoBean> listaCodigoBean) {
        this.listaCodigoBean = listaCodigoBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResNombre() {
        if (listaCentroResNombre == null) {
            listaCentroResNombre = new ArrayList<>();
        }
        return listaCentroResNombre;
    }

    public void setListaCentroResNombre(List<CentroResponsabilidadBean> listaCentroResNombre) {
        this.listaCentroResNombre = listaCentroResNombre;
    }

    public List<CodigoBean> getListaCentroTipoGrupoCR() {
        return listaCentroTipoGrupoCR;
    }

    public void setListaCentroTipoGrupoCR(List<CodigoBean> listaCentroTipoGrupoCR) {
        this.listaCentroTipoGrupoCR = listaCentroTipoGrupoCR;
    }

    public Integer getIdTipoGrupoCR() {
        return idTipoGrupoCR;
    }

    public void setIdTipoGrupoCR(Integer idTipoGrupoCR) {
        this.idTipoGrupoCR = idTipoGrupoCR;
    }

    public List<CentroResponsabilidadBean> getListaCRFiltro() {
        if(listaCRFiltro == null){
            listaCRFiltro = new ArrayList<>();
        }
        return listaCRFiltro;
    }

    public void setListaCRFiltro(List<CentroResponsabilidadBean> listaCRFiltro) {
        this.listaCRFiltro = listaCRFiltro;
    }
        
}
