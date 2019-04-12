package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.TipoPaganteBean;
import pe.marista.sigma.dao.TipoPaganteDAO;

public class TipoPaganteService {

    private TipoPaganteDAO tipoPaganteDAO;

    public List<TipoPaganteBean> obtenerTipoPagante(TipoPaganteBean tipoPaganteBean) throws Exception {
        return tipoPaganteDAO.obtenerTipoPagante(tipoPaganteBean);
    }

    public TipoPaganteDAO getTipoPaganteDAO() {
        return tipoPaganteDAO;
    }

    public void setTipoPaganteDAO(TipoPaganteDAO tipoPaganteDAO) {
        this.tipoPaganteDAO = tipoPaganteDAO;
    }

}
