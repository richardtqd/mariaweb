package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.bean.EventoBean;
import pe.marista.sigma.bean.EventoTipoPaganteBean;
import pe.marista.sigma.bean.TipoPaganteBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.EventoService;
import pe.marista.sigma.service.EventoTipoPaganteService;
import pe.marista.sigma.service.TipoPaganteService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class EventoMB extends BaseMB implements Serializable {

    @PostConstruct
    public void EventoMB() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            obtenerEvento();

            TipoPaganteService tipoPaganteService = BeanFactory.getTipoPaganteService();
            getTipoPaganteBean();
            tipoPaganteBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaTipoPaganteBean();
            listaTipoPaganteBean = tipoPaganteService.obtenerTipoPagante(tipoPaganteBean);
            getEventoTipoPaganteBean();
            eventoTipoPaganteBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    private EventoBean eventoBean;
    private List<EventoBean> listaEventoBean;
    private UsuarioBean usuarioSessionBean;
    private Calendar fechaActual;
    private EventoTipoPaganteBean eventoTipoPaganteBean;
    private List<TipoPaganteBean> listaTipoPaganteBean;
    private TipoPaganteBean tipoPaganteBean;
    private List<EventoTipoPaganteBean> listaEventoTipoPaganteBean;

    //AYUDA
    private Integer objEventoTipoPag;

    //METODOS DE CLASE
    public void cargarDatos() {
        try {
            getEventoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fechaActual = new GregorianCalendar();
            getEventoBean().setFechaIni(fechaActual.getTime());
            getEventoBean().setFechaFin(fechaActual.getTime());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerEvento() {
        try {
            EventoService eventoService = BeanFactory.getEventoService();
            EventoBean evento = new EventoBean();
            evento.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEventoBean = eventoService.obtener(evento);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerEventoPorId(Object object) {
        try {
            eventoBean = (EventoBean) object;
            EventoService eventoService = BeanFactory.getEventoService();
            eventoBean = eventoService.obtenerPorId(eventoBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerEventoPorIdEvento(Object object) {
        try {
            eventoBean = (EventoBean) object;
            EventoService eventoService = BeanFactory.getEventoService();
            eventoBean = eventoService.obtenerPorId(eventoBean);
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            eventoTipoPaganteBean.getEventoBean().setIdEvento(eventoBean.getIdEvento());
            listaEventoTipoPaganteBean = eventoTipoPaganteService.obtenerPorEvento(eventoTipoPaganteBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarEvento() {
        try {
            eventoBean = new EventoBean();
            cargarDatos();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void guardarEvento() {
        try {
            if (eventoBean.getIdEvento() != null) {
                modificarEvento();
            } else if (eventoBean.getIdEvento() == null) {
                insertarEvento();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void insertarEvento() {
        try {
            EventoService eventoService = BeanFactory.getEventoService();
            eventoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            eventoBean.setCreaPor(usuarioSessionBean.getUsuario());
            eventoService.insertar(eventoBean);
            obtenerEvento();
            limpiarEvento();
            cargarDatos();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void modificarEvento() {
        try {
            EventoService eventoService = BeanFactory.getEventoService();
            eventoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            eventoBean.setModiPor(usuarioSessionBean.getUsuario());
            eventoService.actualizar(eventoBean);
            obtenerEvento();
            limpiarEvento();
            cargarDatos();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void eliminarEvento() {
        try {
            EventoService eventoService = BeanFactory.getEventoService();
            eventoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            eventoService.eliminar(eventoBean);
            obtenerEvento();
            limpiarEvento();
            cargarDatos();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void grabar() {
        try {
            if (objEventoTipoPag == null) {
                insertarEventoTipoPag();
            } else {
                modificarEventoTipoPag();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void insertarEventoTipoPag() {
        try {
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            eventoTipoPaganteBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            eventoTipoPaganteBean.setCreaPor(usuarioSessionBean.getUsuario());
            eventoTipoPaganteService.insertar(eventoTipoPaganteBean);
            listaEventoTipoPaganteBean = eventoTipoPaganteService.obtenerPorEvento(eventoTipoPaganteBean);
            limpiarEventoTipoPag();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void modificarEventoTipoPag() {
        try {
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            eventoTipoPaganteBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            eventoTipoPaganteBean.setModiPor(usuarioSessionBean.getUsuario());
            eventoTipoPaganteService.modificar(eventoTipoPaganteBean);
            listaEventoTipoPaganteBean = eventoTipoPaganteService.obtenerPorEvento(eventoTipoPaganteBean);
            limpiarEventoTipoPag();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void eliminarEventoTipoPag() {
        try {
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            eventoTipoPaganteService.eliminar(eventoTipoPaganteBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarEventoTipoPag() {
        try {
            eventoTipoPaganteBean = new EventoTipoPaganteBean();
            eventoTipoPaganteBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerTipoEventoPorId(Object object) {
        try {
            EventoTipoPaganteBean eventoTipo = (EventoTipoPaganteBean) object;
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            eventoTipoPaganteBean = eventoTipoPaganteService.obtenerPorIdEventoTip(eventoTipo);
            getObjEventoTipoPag();
            objEventoTipoPag = 1;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //GET Y SET
    public EventoBean getEventoBean() {
        if (eventoBean == null) {
            eventoBean = new EventoBean();
        }
        return eventoBean;
    }

    public void setEventoBean(EventoBean eventoBean) {
        this.eventoBean = eventoBean;
    }

    public List<EventoBean> getListaEventoBean() {
        if (listaEventoBean == null) {
            listaEventoBean = new ArrayList<>();
        }
        return listaEventoBean;
    }

    public void setListaEventoBean(List<EventoBean> listaEventoBean) {
        this.listaEventoBean = listaEventoBean;
    }

    public UsuarioBean getUsuarioSessionBean() {
        if (usuarioSessionBean == null) {
            usuarioSessionBean = new UsuarioBean();
        }
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public EventoTipoPaganteBean getEventoTipoPaganteBean() {
        if (eventoTipoPaganteBean == null) {
            eventoTipoPaganteBean = new EventoTipoPaganteBean();
        }
        return eventoTipoPaganteBean;
    }

    public void setEventoTipoPaganteBean(EventoTipoPaganteBean eventoTipoPaganteBean) {
        this.eventoTipoPaganteBean = eventoTipoPaganteBean;
    }

    public List<TipoPaganteBean> getListaTipoPaganteBean() {
        if (listaTipoPaganteBean == null) {
            listaTipoPaganteBean = new ArrayList<>();
        }
        return listaTipoPaganteBean;
    }

    public void setListaTipoPaganteBean(List<TipoPaganteBean> listaTipoPaganteBean) {
        this.listaTipoPaganteBean = listaTipoPaganteBean;
    }

    public TipoPaganteBean getTipoPaganteBean() {
        if (tipoPaganteBean == null) {
            tipoPaganteBean = new TipoPaganteBean();
        }
        return tipoPaganteBean;
    }

    public void setTipoPaganteBean(TipoPaganteBean tipoPaganteBean) {
        this.tipoPaganteBean = tipoPaganteBean;
    }

    public List<EventoTipoPaganteBean> getListaEventoTipoPaganteBean() {
        if (listaEventoTipoPaganteBean == null) {
            listaEventoTipoPaganteBean = new ArrayList<>();
        }
        return listaEventoTipoPaganteBean;
    }

    public void setListaEventoTipoPaganteBean(List<EventoTipoPaganteBean> listaEventoTipoPaganteBean) {
        this.listaEventoTipoPaganteBean = listaEventoTipoPaganteBean;
    }

    public Integer getObjEventoTipoPag() {
        return objEventoTipoPag;
    }

    public void setObjEventoTipoPag(Integer objEventoTipoPag) {
        this.objEventoTipoPag = objEventoTipoPag;
    }

}
