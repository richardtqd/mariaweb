package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.TipoPaganteBean;

public interface TipoPaganteDAO {

    public List<TipoPaganteBean> obtenerTipoPagante(TipoPaganteBean tipoPaganteBean) throws Exception;

}
