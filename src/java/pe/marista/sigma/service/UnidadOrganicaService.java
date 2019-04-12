package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.dao.UnidadNegocioDAO;
import pe.marista.sigma.dao.UnidadOrganicaDAO;

public class UnidadOrganicaService {

    //Variable

    private UnidadOrganicaDAO unidadOrganicaDAO;

    //Getter and Setter
    public UnidadOrganicaDAO getUnidadOrganicaDAO() {
        return unidadOrganicaDAO;
    }

    public void setUnidadOrganicaDAO(UnidadOrganicaDAO unidadOrganicaDAO) {
        this.unidadOrganicaDAO = unidadOrganicaDAO;
    }

    //Metodos Logica de unidad organica
    public List<UnidadOrganicaBean> obtenerTodos() throws Exception {
        return unidadOrganicaDAO.obtenerTodos();
    }

    public UnidadOrganicaBean obtenerUnidadOrganicaPorId(UnidadOrganicaBean UnidadOrganicaBean) throws Exception {
        return unidadOrganicaDAO.obtenerUnidadOrganicaPorId(UnidadOrganicaBean);
    }

 //   public List<UnidadOrganicaBean> obtenerPorUnidadNegocio(String uniNeg) throws Exception{
    //       return unidadOrganicaDAO.obtenerPorUnidadNegocio(uniNeg);
    //  }
}
