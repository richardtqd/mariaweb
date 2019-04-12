/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ChequeBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ChequeService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ImpresoraService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
@ManagedBean
@ViewScoped
public class ImpresoraMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ImpresoraMB
     */
    @PostConstruct
    public void ImpresoraMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            obtenerTodos();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoDocumento = new ArrayList<>();
            mapTipoDocumento = new LinkedHashMap<>();
            listaTipoDocumento = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            for (CodigoBean codigo : listaTipoDocumento) {
                mapTipoDocumento.put(codigo.getCodigo(), codigo.getIdCodigo());
            }
            listaEstado = new LinkedHashMap<>();
            listaEstado.put(MaristaConstantes.ESTADO_ACTIVO_DES, MaristaConstantes.ESTADO_ACTIVO);
            listaEstado.put(MaristaConstantes.ESTADO_INACTIVO_DES, MaristaConstantes.ESTADO_INACTIVO);
            
            obtenerCheques();
            
            
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private List<ImpresoraBean> listaImpresoraBean;
    private Map<String, Integer> listaEstado;
    private ImpresoraBean impresoraBean;
    private ImpresoraBean impresoraMod;
    private List<CodigoBean> listaTipoDocumento;
    private List<ChequeBean> listaChequeBean;
    private Map<String, Integer> mapTipoDocumento;

    private UsuarioBean usuarioBean;

    public void obtenerTodos() {
        try {

            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            listaImpresoraBean = impresoraService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            impresoraBean = new ImpresoraBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    public void obtenerCheques() {
        try {

            ChequeService chequeService = BeanFactory.getChequeService();
            getListaChequeBean();
            ChequeBean cheque = new ChequeBean();
            cheque.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaChequeBean=chequeService.obtenerChequeActivos(cheque);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarImpresoraBean() {
        impresoraBean = new ImpresoraBean();
    }

    public void obtenerPorId(ImpresoraBean impresora) {
        try {
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            impresoraBean = impresoraService.buscarPorId(impresora);
            impresoraMod = new ImpresoraBean();
            impresoraBean.setEdit("true");
            impresoraMod.setUnidadNegocioBean(impresoraBean.getUnidadNegocioBean());
            impresoraMod.setImpresora(impresoraBean.getImpresora());
            impresoraMod.setIdTipoDoc(impresoraBean.getIdTipoDoc());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarImpresora() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                if (impresoraBean.getInicio() < impresoraBean.getFin()
                        && impresoraBean.getInicio() <= impresoraBean.getActual()
                        && impresoraBean.getFin() >= impresoraBean.getActual()
                        && impresoraBean.getFin() > impresoraBean.getInicio()
                        && impresoraBean.getActual() >= impresoraBean.getInicio()
                        && impresoraBean.getActual() <= impresoraBean.getFin()) {
                    impresoraBean.setCreaPor(usuarioBean.getUsuario());
                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                    String date = formato.format(new Date());
                    impresoraBean.setCreaFecha(formato.parse(date));
                    impresoraBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                    ImpresoraService impresoraService = BeanFactory.getImpresoraService();

                    impresoraService.insertar(impresoraBean);
                    listaImpresoraBean = impresoraService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    limpiarImpresoraBean();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
//                    new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeImpresoraError", null));
                    new MensajePrime().addInformativeMessagePer("mensajeImpresoraError");
                }

            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarImpresora() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                if (impresoraBean.getInicio() < impresoraBean.getFin()
                        && impresoraBean.getInicio() <= impresoraBean.getActual()
                        && impresoraBean.getFin() >= impresoraBean.getActual()
                        && impresoraBean.getFin() > impresoraBean.getInicio()
                        && impresoraBean.getActual() >= impresoraBean.getInicio()
                        && impresoraBean.getActual() <= impresoraBean.getFin()) {
                    impresoraBean.setImpresora(impresoraMod.getImpresora());
                    impresoraBean.setIdTipoDoc(impresoraMod.getIdTipoDoc());
                    impresoraBean.setModiPor(usuarioBean.getUsuario());
                    impresoraService.actualizar(impresoraBean);
                    listaImpresoraBean = impresoraService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    limpiarImpresoraBean();
                    impresoraMod = new ImpresoraBean();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                    new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeImpresoraError", null));
                }

            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarImpresora() {
        try {
            if (impresoraBean.getUnidadNegocioBean().getUniNeg() == null) {
                insertarImpresora();
            } else if ("".equals(impresoraBean.getUnidadNegocioBean().getUniNeg())) {
                insertarImpresora();
            } else {
                modificarImpresora();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public String eliminarImpresora() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                impresoraBean.setImpresora(impresoraMod.getImpresora());
                impresoraBean.setIdTipoDoc(impresoraMod.getIdTipoDoc());
                impresoraBean.setUnidadNegocioBean(impresoraMod.getUnidadNegocioBean());
                impresoraService.eliminar(impresoraBean);
                listaImpresoraBean = impresoraService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarImpresoraBean();
                impresoraMod = new ImpresoraBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoImpresora() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                impresoraBean.setImpresora(impresoraMod.getImpresora());
                impresoraBean.setIdTipoDoc(impresoraMod.getIdTipoDoc());
                impresoraBean.setUnidadNegocioBean(impresoraMod.getUnidadNegocioBean());
                impresoraBean.setStatus(impresoraMod.getStatus());
                impresoraBean.setModiPor(usuarioBean.getUsuario());
                impresoraService.cambiarEstado(getImpresoraBean());
                listaImpresoraBean = impresoraService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarImpresoraBean();
                impresoraMod = new ImpresoraBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerImpresora(Object docIngresoSerie) {
        try {
            impresoraBean = new ImpresoraBean();
            impresoraMod = new ImpresoraBean();
            impresoraBean = (ImpresoraBean) docIngresoSerie;
            impresoraBean.setEdit("true");
            impresoraMod.setUnidadNegocioBean(impresoraBean.getUnidadNegocioBean());
            impresoraMod.setImpresora(impresoraBean.getImpresora());
            impresoraMod.setIdTipoDoc(impresoraBean.getIdTipoDoc());
            impresoraMod.setStatus(impresoraBean.getStatus());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
             ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            impresoraBean = new ImpresoraBean();
            impresoraMod = new ImpresoraBean();
            impresoraBean = (ImpresoraBean) event.getObject();
            impresoraBean.setEdit("true");
            impresoraMod.setUnidadNegocioBean(impresoraBean.getUnidadNegocioBean());
            impresoraMod.setImpresora(impresoraBean.getImpresora());
            impresoraMod.setIdTipoDoc(impresoraBean.getIdTipoDoc());
            impresoraMod.setStatus(impresoraBean.getStatus());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    
    public void actualizaChequera() {
        try {
            ChequeService chequeService= BeanFactory.getChequeService();
            getListaChequeBean();
            ChequeBean cheque = new ChequeBean();
            cheque.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaChequeBean = chequeService.obtenerChequeActivos(cheque); 
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    public List<ImpresoraBean> getListaImpresoraBean() {
        return listaImpresoraBean;
    }

    public void setListaImpresoraBean(List<ImpresoraBean> listaImpresoraBean) {
        this.listaImpresoraBean = listaImpresoraBean;
    }

    public Map<String, Integer> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(Map<String, Integer> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public ImpresoraBean getImpresoraBean() {
        if (impresoraBean == null) {
            impresoraBean = new ImpresoraBean();
        }
        return impresoraBean;
    }

    public void setImpresoraBean(ImpresoraBean impresoraBean) {
        this.impresoraBean = impresoraBean;
    }

    public ImpresoraBean getImpresoraMod() {
        if (impresoraMod == null) {
            impresoraMod = new ImpresoraBean();
        }
        return impresoraMod;
    }

    public void setImpresoraMod(ImpresoraBean impresoraMod) {
        this.impresoraMod = impresoraMod;
    }

    public List<CodigoBean> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<CodigoBean> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public Map<String, Integer> getMapTipoDocumento() {
        return mapTipoDocumento;
    }

    public void setMapTipoDocumento(Map<String, Integer> mapTipoDocumento) {
        this.mapTipoDocumento = mapTipoDocumento;
    }

    public List<ChequeBean> getListaChequeBean() {
        if (listaChequeBean == null) {
            listaChequeBean = new ArrayList<>();
        }
        return listaChequeBean;
    }

    public void setListaChequeBean(List<ChequeBean> listaChequeBean) {
        this.listaChequeBean = listaChequeBean;
    }

}
