package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.dao.CotizacionDAO;
import pe.marista.sigma.dao.OrdenCompraDAO;
import pe.marista.sigma.factory.BeanFactory;

public class CotizacionService {

    private CotizacionDAO cotizacionDAO;
    private OrdenCompraDAO ordenCompraDAO;

    //Getter and Setter
    public CotizacionDAO getCotizacionDAO() {
        return cotizacionDAO;
    }

    public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
        this.cotizacionDAO = cotizacionDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(CotizacionBean cotizacionBean) throws Exception {
        cotizacionDAO.insertar(cotizacionBean); 
    } 

    @Transactional
    public void modificar(CotizacionBean cotizacionBean) throws Exception {
        cotizacionDAO.actualizar(cotizacionBean);
    }
    @Transactional
    public void changeStatusCoti(List<DetCotizacionBean> lista) throws Exception {
        for(DetCotizacionBean list: lista)
        {
         cotizacionDAO.changeStatusCoti(list.getIdCotizacion(),list.getUnidadNegocioBean().getUniNeg(),list.getCotizacionBean().getFlgAceptado(),list.getCotizacionBean().getModiPor());
        }
        
       
    }

    @Transactional
//    public void cambiarEstadoOrdenCompraRegistrado(CotizacionBean cotizacionBean) throws Exception { 
//         cotizacionBean.getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_COMPRAREQUERIMIENTO);
//        cotizacionBean.getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
//        cotizacionDAO.cambiarEstadoOrdenCompraRegistrado(cotizacionBean);
//    }

    public List<CotizacionBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception {
        return cotizacionDAO.obtenerTodosPorUniNeg(uniNeg);
    }

    public List<CotizacionBean> obtenerTodosSolDes(CotizacionBean bean) throws Exception {
        return cotizacionDAO.obtenerTodosSolDes(bean);
    }

    public List<CotizacionBean> obtenerTodosM(CotizacionBean bean) throws Exception {
        return cotizacionDAO.obtenerTodosM(bean);
    }
    
    public List<CotizacionBean> obtenerTodosMCotizacion(CotizacionBean bean) throws Exception {
        return cotizacionDAO.obtenerTodosMCotizacion(bean);
    }
    
    public List<CotizacionBean> obtenerTodosMCotizacionAceptadas(CotizacionBean bean) throws Exception {
        return cotizacionDAO.obtenerTodosMCotizacionAceptadas(bean);
    }

    public List<CotizacionBean> obtenerPorFiltro(CotizacionBean cotizacion) throws Exception {
        return cotizacionDAO.obtenerPorFiltro(cotizacion);
    }

    public List<CotizacionBean> obtenerListaPorId(CotizacionBean cotizacionBean) throws Exception {
        return cotizacionDAO.obtenerListaPorId(cotizacionBean);
    }

    public Integer obtenerUltimo(String uniNeg) throws Exception {
        return cotizacionDAO.obtenerUltimo(uniNeg);
    }
    
    public Integer obtenerUltimoCoti(Integer idCotizacion,String uniNeg) throws Exception {
        return cotizacionDAO.obtenerUltimoCoti(idCotizacion,uniNeg);
    }

    public CotizacionBean obtenerPorId(CotizacionBean cotizacionBean) throws Exception {
        return cotizacionDAO.obtenerPorId(cotizacionBean);
    }

    public CotizacionBean obtenerRucPorReq(Integer idRequerimiento,String uniNeg) throws Exception {
        return cotizacionDAO.obtenerRucPorReq(idRequerimiento,uniNeg);
    }
    
    public CotizacionBean obtenerPorIdCotiParaOrden(Integer idCotizacion) throws Exception {
        return cotizacionDAO.obtenerPorIdCotiParaOrden(idCotizacion);
    }

    public SolicitudLogisticoBean obtenerPorSol(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception {
        return cotizacionDAO.obtenerPorSol(solicitudLogisticoBean);
    }
    
    public String obtenerUltimoCotizacion(String uniNeg) throws Exception {
        return cotizacionDAO.obtenerUltimoCotizacion(uniNeg);
    } 
}
