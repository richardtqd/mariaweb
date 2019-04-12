package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.EventoTipoPaganteBean;

public interface EventoTipoPaganteDAO {

    public List<EventoTipoPaganteBean> obtener(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public EventoTipoPaganteBean obtenerPorIdEventoTip(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public List<EventoTipoPaganteBean> obtenerPorEvento(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public List<EventoTipoPaganteBean> obtenerPorId(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public void insertar(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public void modificar(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public void eliminar(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public Integer obtenerNumeroAsig(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

    public Integer obtenerNumeroIni(EventoTipoPaganteBean eventoTipoPaganteBean) throws Exception;

}
