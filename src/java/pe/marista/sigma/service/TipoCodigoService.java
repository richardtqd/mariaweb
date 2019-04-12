package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.dao.CodigoDAO;
import pe.marista.sigma.dao.TipoCodigoDAO;

/**
 *
 * @author Administrador
 */
public class TipoCodigoService {

    private TipoCodigoDAO tipoCodigoDAO;
    private CodigoDAO codigoDAO;

    //GEtter and SEtter
    public TipoCodigoDAO getTipoCodigoDAO() {
        return tipoCodigoDAO;
    }
    public void setTipoCodigoDAO(TipoCodigoDAO tipoCodigoDAO) {
        this.tipoCodigoDAO = tipoCodigoDAO;
    }
   
    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(TipoCodigoBean tipoCodigoBean) throws Exception{
        tipoCodigoDAO.insertar(tipoCodigoBean);
    }
    @Transactional
    public void modificar(TipoCodigoBean tipoCodigoBean) throws Exception{
        tipoCodigoDAO.actualizar(tipoCodigoBean);
    }
    @Transactional
    public void eliminar(TipoCodigoBean tipoCodigoBean) throws Exception{
        codigoDAO.eliminarPorTipo(tipoCodigoBean);
        tipoCodigoDAO.eliminar(tipoCodigoBean);
    }
    public List<TipoCodigoBean> obtenerTodos() throws Exception{    
        return tipoCodigoDAO.obtenerTodos();
    } 
    public List<TipoCodigoBean> obtenerPorTipo(String idTipoCodigo) throws Exception {
        return tipoCodigoDAO.obtenerPorTipo(idTipoCodigo);
    } 
    public List<TipoCodigoBean> obtenerFiltro(TipoCodigoBean tipoCodigoBean) throws Exception{    
        return tipoCodigoDAO.obtenerFiltro(tipoCodigoBean);
    }
    public TipoCodigoBean obtenerPorId(TipoCodigoBean tipoCodigoBean) throws Exception{    
        return tipoCodigoDAO.obtenerPorId(tipoCodigoBean);
    }
    public CodigoDAO getCodigoDAO() {
        return codigoDAO;
    }
    public void setCodigoDAO(CodigoDAO codigoDAO) {
        this.codigoDAO = codigoDAO;
    }
}
