package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.EventoBean;

public interface EventoDAO {

    public List<EventoBean> obtener(EventoBean eventoBean) throws Exception;

    public List<EventoBean> obtenerEventoDefecto(EventoBean eventoBean) throws Exception;

    public void insertar(EventoBean eventoBean) throws Exception;

    public void actualizar(EventoBean eventoBean) throws Exception;

    public EventoBean obtenerPorId(EventoBean eventoBean) throws Exception;

    public void eliminar(EventoBean eventoBean) throws Exception;

}
