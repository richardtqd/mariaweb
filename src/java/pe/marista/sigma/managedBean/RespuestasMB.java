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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import pe.marista.sigma.bean.PreguntaBean;
import pe.marista.sigma.bean.RespuestasBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.RespuestasService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class RespuestasMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of RespuestasMB
     */
    @PostConstruct
    public void RespuestasMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Varialbes y Propiedades
    private RespuestasBean respuestasBean;
    private List<RespuestasBean> listaRespuestasBean;

    private PreguntaBean preguntaBean;
    private List<PreguntaBean> listaPreguntaBean;
    private UsuarioBean usuarioLogin;

    public void obtenerPreguntas() {
        try {
            RespuestasService respuestasService = BeanFactory.getRespuestasService();
            getListaPreguntaBean();
            listaPreguntaBean = respuestasService.obtenerPregunta();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarRespuestas() {
        respuestasBean = new RespuestasBean();
    }

    //insetar solo preguntass
    public String insertarRespuestas() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                RespuestasService respuestasService = BeanFactory.getRespuestasService();
                getListaPreguntaBean();
                listaPreguntaBean = respuestasService.obtenerPregunta();
                RespuestasBean resp = new RespuestasBean();
                resp = respuestasService.obtenerUltimoOrden();
                if (resp==null) {
                      resp=new RespuestasBean();
                      resp.setOrden(0);
                }
                respuestasService.insertarRespuestas(respuestasBean, listaPreguntaBean, resp);
                listaRespuestasBean = respuestasService.obtenerRespuestasPorOrden(resp.getOrden() + 1);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarRespuestas() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                RespuestasService respuestasService = BeanFactory.getRespuestasService();
                respuestasService.modificarRespuestas(respuestasBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            RespuestasService respuestasService = BeanFactory.getRespuestasService();
            respuestasBean = new RespuestasBean();
            respuestasBean.setIdRespuesta(((RespuestasBean) event.getObject()).getIdRespuesta());
            respuestasBean.setRta1(((RespuestasBean) event.getObject()).getRta1());
            respuestasBean.setRta2(((RespuestasBean) event.getObject()).getRta2());
            respuestasBean.setRta3(((RespuestasBean) event.getObject()).getRta3());
            respuestasBean.setRta4(((RespuestasBean) event.getObject()).getRta4());
            respuestasService.modificarRespuestas(respuestasBean);
            FacesMessage msg = new FacesMessage("Pregunta:", ((RespuestasBean) event.getObject()).getPreguntaBean().getIdPregunta().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            respuestasBean = new RespuestasBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void grabar(RespuestasBean bean) {
        try {
            RespuestasService respuestasService = BeanFactory.getRespuestasService();
            RespuestasBean rta= new RespuestasBean();
            rta.setIdRespuesta(bean.getIdRespuesta());
            rta.setRta1(bean.getRta1());
            rta.setRta2(bean.getRta2());
            rta.setRta3(bean.getRta3());
            rta.setRta4(bean.getRta4());
            respuestasService.modificarRespuestas(rta);   
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Pregunta Cancelada", ((RespuestasBean) event.getObject()).getPreguntaBean().getIdPregunta().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String eliminarRespuestas() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                RespuestasService respuestasService = BeanFactory.getRespuestasService();
                respuestasService.eliminarRespuestas(respuestasBean);
//                listaRespuestasBean = respuestasService.obtenerTodos();
//                limpiarRespuestas();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //getter and setter
    public RespuestasBean getRespuestasBean() {
        if (respuestasBean == null) {
            respuestasBean = new RespuestasBean();
        }
        return respuestasBean;
    }

    public void setRespuestasBean(RespuestasBean respuestasBean) {
        this.respuestasBean = respuestasBean;
    }

    public List<RespuestasBean> getListaRespuestasBean() {
        if (listaRespuestasBean == null) {
            listaRespuestasBean = new ArrayList<>();
        }
        return listaRespuestasBean;
    }

    public void setListaRespuestasBean(List<RespuestasBean> listaRespuestasBean) {
        this.listaRespuestasBean = listaRespuestasBean;
    }

    public PreguntaBean getPreguntaBean() {
        if (preguntaBean == null) {
            preguntaBean = new PreguntaBean();
        }
        return preguntaBean;
    }

    public void setPreguntaBean(PreguntaBean preguntaBean) {
        this.preguntaBean = preguntaBean;
    }

    public List<PreguntaBean> getListaPreguntaBean() {
        if (listaPreguntaBean == null) {
            listaPreguntaBean = new ArrayList<>();
        }
        return listaPreguntaBean;
    }

    public void setListaPreguntaBean(List<PreguntaBean> listaPreguntaBean) {
        this.listaPreguntaBean = listaPreguntaBean;
    }

}
