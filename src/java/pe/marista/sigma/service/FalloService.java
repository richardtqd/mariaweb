package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.FalloBean;
import pe.marista.sigma.dao.FalloDAO;

public class FalloService {

    private FalloDAO falloDAO;

    public List<FalloBean> obtenerPorUniNeg(FalloBean falloBean) throws Exception {
        return falloDAO.obtenerPorUniNeg(falloBean);
    }

    public List<FalloBean> filtrarFallo(FalloBean falloBean) throws Exception {
        return falloDAO.filtrarFallo(falloBean);
    }

    public FalloDAO getFalloDAO() {
        return falloDAO;
    }

    public void setFalloDAO(FalloDAO falloDAO) {
        this.falloDAO = falloDAO;
    }

}
