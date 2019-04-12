package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.EventoBean;
import pe.marista.sigma.dao.EventoDAO;

public class EventoService {

    private EventoDAO eventoDAO;

    public List<EventoBean> obtener(EventoBean eventoBean) throws Exception {
        return eventoDAO.obtener(eventoBean);
    }

    public List<EventoBean> obtenerEventoDefecto(EventoBean eventoBean) throws Exception {
        return eventoDAO.obtenerEventoDefecto(eventoBean);
    }
 
    @Transactional
    public void insertar(EventoBean eventoBean) throws Exception {
        eventoDAO.insertar(eventoBean);
    }

    @Transactional
    public void actualizar(EventoBean eventoBean) throws Exception {
        eventoDAO.actualizar(eventoBean);
    }

    public EventoBean obtenerPorId(EventoBean eventoBean) throws Exception {
        return eventoDAO.obtenerPorId(eventoBean);
    }
    
    @Transactional
    public void eliminar(EventoBean eventoBean) throws Exception {
        eventoDAO.eliminar(eventoBean);
    }

    public EventoDAO getEventoDAO() {
        return eventoDAO;
    }

    public void setEventoDAO(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

}
