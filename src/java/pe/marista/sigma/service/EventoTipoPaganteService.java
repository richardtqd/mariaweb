package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EventoTipoPaganteBean;
import pe.marista.sigma.dao.EventoTipoPaganteDAO;

public class EventoTipoPaganteService {

    private EventoTipoPaganteDAO eventoTipoPaganteDAO;

    public List<EventoTipoPaganteBean> obtener(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        return eventoTipoPaganteDAO.obtener(eventoTipoPaganteBean);
    }

    public List<EventoTipoPaganteBean> obtenerPorId(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        return eventoTipoPaganteDAO.obtenerPorId(eventoTipoPaganteBean);
    }

    public List<EventoTipoPaganteBean> obtenerPorEvento(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        return eventoTipoPaganteDAO.obtenerPorEvento(eventoTipoPaganteBean);
    }

    @Transactional
    public void insertar(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        eventoTipoPaganteDAO.insertar(eventoTipoPaganteBean);
    }

    public Integer obtenerNumeroAsig(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        return eventoTipoPaganteDAO.obtenerNumeroAsig(eventoTipoPaganteBean);
    }

    public Integer obtenerNumeroIni(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        return eventoTipoPaganteDAO.obtenerNumeroIni(eventoTipoPaganteBean);
    }

    public EventoTipoPaganteBean obtenerPorIdEventoTip(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        return eventoTipoPaganteDAO.obtenerPorIdEventoTip(eventoTipoPaganteBean);
    }

    @Transactional
    public void modificar(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        eventoTipoPaganteDAO.modificar(eventoTipoPaganteBean);
    }

    @Transactional
    public void eliminar(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception {
        eventoTipoPaganteDAO.eliminar(eventoTipoPaganteBean);
    }

    public EventoTipoPaganteDAO getEventoTipoPaganteDAO() {
        return eventoTipoPaganteDAO;
    }

    public void setEventoTipoPaganteDAO(EventoTipoPaganteDAO eventoTipoPaganteDAO) {
        this.eventoTipoPaganteDAO = eventoTipoPaganteDAO;
    }

}
