package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ContratoAdquisicionRepBean;
import pe.marista.sigma.bean.reporte.ContratoRepBean;
import pe.marista.sigma.bean.reporte.DetContratoRepBean;
import pe.marista.sigma.bean.reporte.DetOrdenCompraRepBean;
import pe.marista.sigma.bean.reporte.DetContratoAdquisicionRepBean;
import pe.marista.sigma.dao.OrdenCompraDetalleDAO;
import pe.marista.sigma.util.MaristaUtils;

public class OrdenCompraDetalleService {

    private OrdenCompraDetalleDAO ordenCompraDetalleDAO;

    //Getter and Setter
    public OrdenCompraDetalleDAO getOrdenCompraDetalleDAO() {
        return ordenCompraDetalleDAO;
    }

    public void setOrdenCompraDetalleDAO(OrdenCompraDetalleDAO ordenCompraDetalleDAO) {
        this.ordenCompraDetalleDAO = ordenCompraDetalleDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(Integer idOrdenCompra, Integer anio, List<OrdenCompraDetalleBean> listaDetalle) throws Exception {
        for (OrdenCompraDetalleBean listaDetalle1 : listaDetalle) {
          
            listaDetalle1.setIdOrdenCompra(idOrdenCompra);  
            listaDetalle1.setIdRequerimiento(listaDetalle1.getSolicitudLogisticoBean().getIdRequerimiento());
             listaDetalle1.setIdDetRequerimiento(listaDetalle1.getSolicitudLogDetalleBean().getIdDetRequerimiento());
//             listaDetalle1.getSolicitudLogDetalleBean().setFlgComprado(Boolean.TRUE);
//             listaDetalle1.setFlgComprado(Boolean.TRUE);
            // SolicitudLogDetalleBean solicitudLogDetalleBean= new SolicitudLogDetalleBean();
            listaDetalle1.setAnio(anio);
         
//            if (listaDetalle1.isItemFact() == true) {
//                listaDetalle1.setItemFact(true);
//            } else {
//                listaDetalle1.setItemFact(false);
//            }
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDetalle1.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
            ordenCompraDetalleDAO.insertar(listaDetalle1);
        }
    }
    
    //cotizacion
    @Transactional
    public void insertarCotizacionOrden(Integer idOrdenCompra, Integer anio, List<OrdenCompraDetalleBean> listaDetalle) throws Exception {
        for (OrdenCompraDetalleBean listaDetalle1 : listaDetalle) {
          
            listaDetalle1.setIdOrdenCompra(idOrdenCompra);  
            listaDetalle1.setIdRequerimiento(listaDetalle1.getSolicitudLogisticoBean().getIdRequerimiento());
             listaDetalle1.setIdDetRequerimiento(listaDetalle1.getSolicitudLogDetalleBean().getIdDetRequerimiento()); 
            listaDetalle1.setAnio(anio); 
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDetalle1.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
            ordenCompraDetalleDAO.insertarCotizacionOrden(listaDetalle1);
        }
    }

    @Transactional
    public void eliminar(OrdenCompraBean ordenCompraBean) throws Exception {
        ordenCompraDetalleDAO.eliminar(ordenCompraBean);
    }
    @Transactional
    public void modificarFactura(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception {
        ordenCompraDetalleDAO.modificarFactura(ordenCompraDetalleBean);
    }

    public Integer obtenerUltimo(Integer idOrdenCompra,String uniNeg) throws Exception {
        return ordenCompraDetalleDAO.obtenerUltimo(idOrdenCompra,uniNeg);
    }

    public List<OrdenCompraDetalleBean> obtenerListaPorId(Integer idDetOrdenCompra) throws Exception {
        return ordenCompraDetalleDAO.obtenerListaPorId(idDetOrdenCompra);
    }
    public List<OrdenCompraDetalleBean> obtenerTodos(String uniNeg) throws Exception {
        return ordenCompraDetalleDAO.obtenerTodos(uniNeg);
    }
     
    public List<OrdenCompraDetalleBean> obtenerPorOrden(Integer idOrdenCompra,String uniNeg) throws Exception {
        return ordenCompraDetalleDAO.obtenerPorOrden(idOrdenCompra,uniNeg);
    }
    
    public List<OrdenCompraDetalleBean> obtenerPorOrdenFiltro(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception {
        return ordenCompraDetalleDAO.obtenerPorOrdenFiltro(ordenCompraDetalleBean);
    }
     public OrdenCompraDetalleBean obtenerPorId(OrdenCompraDetalleBean ordenCompraDetalleBean) throws Exception {
        return ordenCompraDetalleDAO.obtenerPorId(ordenCompraDetalleBean);
    }

    public List<OrdenCompraDetalleBean> obtenerTodosProveedores(OrdenCompraDetalleBean bean) throws Exception {
        return ordenCompraDetalleDAO.obtenerTodosProveedores(bean);
    }
    
     public List<OrdenCompraDetalleBean> obtenerTodosItems(OrdenCompraDetalleBean bean) throws Exception {
        return ordenCompraDetalleDAO.obtenerTodosItems(bean);
     }

    public List<DetOrdenCompraRepBean> obtenerOrdenCompra(Integer idOrdenCompra, String uniNeg) throws Exception {
        return ordenCompraDetalleDAO.obtenerOrdenCompra(idOrdenCompra, uniNeg);
    }

    public List<ContratoRepBean> obtenerContrato(String ruc, String uniNeg, Integer idOrdenCompra) throws Exception {
        return ordenCompraDetalleDAO.obtenerContrato(ruc, uniNeg, idOrdenCompra);
    }

    public List<DetContratoRepBean> obtenerDetContrato(String uniNeg, Integer idOrdenCompra) throws Exception {
        return ordenCompraDetalleDAO.obtenerDetContrato( uniNeg, idOrdenCompra);
    }

    public List<ContratoAdquisicionRepBean> obtenerContratoAdqui(String ruc, String uniNeg, Integer idOrdenCompra) throws Exception {
        return ordenCompraDetalleDAO.obtenerContratoAdqui(ruc, uniNeg, idOrdenCompra);
    }

    public List<DetContratoAdquisicionRepBean> obtenerDetContratoAdquisicion(String uniNeg, Integer idOrdenCompra) throws Exception {
        return ordenCompraDetalleDAO.obtenerDetContratoAdquisicion(uniNeg, idOrdenCompra);
    } 
 
}
