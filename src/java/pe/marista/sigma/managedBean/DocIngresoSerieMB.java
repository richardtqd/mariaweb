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
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocIngresoSerieBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DocIngresoSerieService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
@ManagedBean
@RequestScoped
public class DocIngresoSerieMB extends BaseMB implements Serializable {

    @PostConstruct
    public void DocIngresoSerieMB() {
        try {
            obtenerTodos();
            DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
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
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }
    private List<DocIngresoSerieBean> listaDocIngresoSerieBean;
    private Map<String, Integer> listaEstado;
    private DocIngresoSerieBean docIngresoSerieBean;
    private DocIngresoSerieBean docIngresoSerieMod;
    private List<CodigoBean> listaTipoDocumento;
    private Map<String, Integer> mapTipoDocumento;

    public void obtenerTodos() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");            
            DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
            listaDocIngresoSerieBean = docIngresoSerieService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            docIngresoSerieBean = new DocIngresoSerieBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarDocIngresoSeriebean() {
        docIngresoSerieBean = new DocIngresoSerieBean();
    }

    public void obtenerPorId(DocIngresoSerieBean docIngreso) {
        try {
            DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
            docIngresoSerieBean = docIngresoSerieService.buscarPorId(docIngreso);
            docIngresoSerieMod = new DocIngresoSerieBean();
            docIngresoSerieBean.setEdit("true");
            docIngresoSerieMod.setUnidadNegocioBean(docIngresoSerieBean.getUnidadNegocioBean());
            docIngresoSerieMod.setSerie(docIngresoSerieBean.getSerie());
            docIngresoSerieMod.setIdTipoDoc(docIngresoSerieBean.getIdTipoDoc());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarDocIngresoSerie() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                docIngresoSerieBean.setCreaPor(usuarioBean.getUsuario());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                docIngresoSerieBean.setCreaFecha(formato.parse(date));
                docIngresoSerieBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
                docIngresoSerieService.insertar(docIngresoSerieBean);
                listaDocIngresoSerieBean = docIngresoSerieService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarDocIngresoSeriebean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarDocIngresoSerie() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
                docIngresoSerieBean.setSerie(docIngresoSerieMod.getSerie());
                docIngresoSerieBean.setIdTipoDoc(docIngresoSerieMod.getIdTipoDoc());
                docIngresoSerieBean.setModiPor(usuarioBean.getUsuario());
                docIngresoSerieService.actualizar(docIngresoSerieBean);
                listaDocIngresoSerieBean = docIngresoSerieService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarDocIngresoSeriebean();
                docIngresoSerieMod = new DocIngresoSerieBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarDocIngresoSerie() {
        try {
            if (docIngresoSerieBean.getUnidadNegocioBean().getUniNeg() == null) {
                insertarDocIngresoSerie();
            } 
            else if ("".equals(docIngresoSerieBean.getUnidadNegocioBean().getUniNeg())) {
                insertarDocIngresoSerie();
            }
            else {
                modificarDocIngresoSerie();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public String eliminarDocIngresoSerie() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
                docIngresoSerieBean.setSerie(docIngresoSerieMod.getSerie());
                docIngresoSerieBean.setIdTipoDoc(docIngresoSerieMod.getIdTipoDoc());
                docIngresoSerieBean.setUnidadNegocioBean(docIngresoSerieMod.getUnidadNegocioBean());
                docIngresoSerieService.eliminar(docIngresoSerieBean);
                listaDocIngresoSerieBean = docIngresoSerieService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarDocIngresoSeriebean();
                docIngresoSerieMod = new DocIngresoSerieBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoDocIngresoSerie() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
                docIngresoSerieBean.setSerie(docIngresoSerieMod.getSerie());
                docIngresoSerieBean.setIdTipoDoc(docIngresoSerieMod.getIdTipoDoc());
                docIngresoSerieBean.setUnidadNegocioBean(docIngresoSerieMod.getUnidadNegocioBean());
                docIngresoSerieBean.setStatus(docIngresoSerieMod.getStatus());
                docIngresoSerieBean.setModiPor(usuarioBean.getUsuario());
                docIngresoSerieService.cambiarEstado(getDocIngresoSerieBean());                
                listaDocIngresoSerieBean = docIngresoSerieService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarDocIngresoSeriebean();
                docIngresoSerieMod = new DocIngresoSerieBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerDocIngresoSerie(Object docIngresoSerie) {
        try {
            docIngresoSerieBean = new DocIngresoSerieBean();
            docIngresoSerieMod = new DocIngresoSerieBean();
            docIngresoSerieBean = (DocIngresoSerieBean) docIngresoSerie;
            docIngresoSerieBean.setEdit("true");
            docIngresoSerieMod.setUnidadNegocioBean(docIngresoSerieBean.getUnidadNegocioBean());
            docIngresoSerieMod.setSerie(docIngresoSerieBean.getSerie());
            docIngresoSerieMod.setIdTipoDoc(docIngresoSerieBean.getIdTipoDoc());
            docIngresoSerieMod.setStatus(docIngresoSerieBean.getStatus());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            docIngresoSerieBean = new DocIngresoSerieBean();
            docIngresoSerieMod = new DocIngresoSerieBean();
            docIngresoSerieBean = (DocIngresoSerieBean) event.getObject();
            docIngresoSerieBean.setEdit("true");
            docIngresoSerieMod.setUnidadNegocioBean(docIngresoSerieBean.getUnidadNegocioBean());
            docIngresoSerieMod.setSerie(docIngresoSerieBean.getSerie());
            docIngresoSerieMod.setIdTipoDoc(docIngresoSerieBean.getIdTipoDoc());
            docIngresoSerieMod.setStatus(docIngresoSerieBean.getStatus());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Map<String, Integer> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(Map<String, Integer> listaEstado) {
        this.listaEstado = listaEstado;
    }

    public DocIngresoSerieBean getDocIngresoSerieBean() {
        if (docIngresoSerieBean == null) {
            docIngresoSerieBean = new DocIngresoSerieBean();
        }
        return docIngresoSerieBean;
    }

    public void setDocIngresoSerieBean(DocIngresoSerieBean docIngresoSerieBean) {
        this.docIngresoSerieBean = docIngresoSerieBean;
    }

    public List<CodigoBean> getListaTipoDocumento() {
        if (listaTipoDocumento == null) {
            listaTipoDocumento = new ArrayList<>();
        }
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

    public List<DocIngresoSerieBean> getListaDocIngresoSerieBean() {
        if (listaDocIngresoSerieBean == null) {
            listaDocIngresoSerieBean = new ArrayList<>();
        }
        return listaDocIngresoSerieBean;
    }

    public void setListaDocIngresoSerieBean(List<DocIngresoSerieBean> listaDocIngresoSerieBean) {
        this.listaDocIngresoSerieBean = listaDocIngresoSerieBean;
    }

    public DocIngresoSerieBean getDocIngresoSerieMod() {
        if(docIngresoSerieMod == null)
        {
            docIngresoSerieMod = new DocIngresoSerieBean();
        }
        return docIngresoSerieMod;
    }

    public void setDocIngresoSerieMod(DocIngresoSerieBean docIngresoSerieMod) {
        this.docIngresoSerieMod = docIngresoSerieMod;
    }

}
