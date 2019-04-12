package pe.marista.sigma.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.IndicadorBean;
import pe.marista.sigma.dao.IndicadorDAO;

public class IndicadorService {

    // Variables
    private IndicadorDAO indicadorDAO;

    public List<IndicadorBean> obtenerTodos() throws Exception {
        return indicadorDAO.obtenerTodos();
    }

    public List<IndicadorBean> obtenerPorTipoUso(Integer idCodigo) throws Exception {
        return indicadorDAO.obtenerPorTipoUso(idCodigo);
    } 
    
    public List<IndicadorBean> obtenerPorFiltro(IndicadorBean indicadorBean) throws Exception {
        return indicadorDAO.obtenerPorFiltro(indicadorBean);
    }

    public IndicadorBean buscarPorId(Integer idIndicador) throws Exception {
        return indicadorDAO.buscarPorId(idIndicador);
    }

    @Transactional
    public void insertar(IndicadorBean indicadorBean) throws Exception {
        indicadorDAO.insertar(indicadorBean);
    }

    @Transactional
    public void actualizar(IndicadorBean indicadorBean) throws Exception {
        indicadorDAO.actualizar(indicadorBean);
    }

    public String obtenerCodigo(String codigo) throws Exception {
        return indicadorDAO.obtenerCodigo(codigo);
    }

    public void eliminar(Integer idIndicador) throws Exception {
        indicadorDAO.eliminar(idIndicador);
    }

    //Métodos
    public IndicadorDAO getIndicadorDAO() {
        return indicadorDAO;
    }

    public void setIndicadorDAO(IndicadorDAO indicadorDAO) {
        this.indicadorDAO = indicadorDAO;
    }

}
