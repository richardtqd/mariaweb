package pe.marista.sigma.service;

import pe.marista.sigma.bean.RecibosMoraBean;
import pe.marista.sigma.dao.RecibosMoraDAO;

public class RecibosMoraService {

    private RecibosMoraDAO recibosMoraDAO;

    public RecibosMoraDAO getRecibosMoraDAO() {
        return recibosMoraDAO;
    }

    public void setRecibosMoraDAO(RecibosMoraDAO recibosMoraDAO) {
        this.recibosMoraDAO = recibosMoraDAO;
    }

    public Integer obtenerUltimo(String uniNeg) throws Exception {
        return recibosMoraDAO.obtenerUltimo(uniNeg);
    }

    public void insertarRecibosMora(RecibosMoraBean recibosMoraBean) throws Exception {
        recibosMoraDAO.insertarRecibosMora(recibosMoraBean);
    }

    public RecibosMoraBean obtenerId(Integer nroDocMora, String uniNeg, String serieMora, Integer idDocIngreso) throws Exception {
        return recibosMoraDAO.obtenerId(nroDocMora, uniNeg, serieMora, idDocIngreso);
    }

    public String obtenerIdDocIngreso(Integer idDocIngreso) throws Exception {
        return recibosMoraDAO.obtenerIdDocIngreso(idDocIngreso);
    } 
    
}
