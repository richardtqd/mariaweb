package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.SolicitudLogisticoRepBean;
import pe.marista.sigma.dao.CatalogoDAO;
import pe.marista.sigma.dao.InventarioActivoDAO;
import pe.marista.sigma.dao.InventarioAlmacenDAO;
import pe.marista.sigma.dao.SolicitudLogisticoDetalleDAO;
import pe.marista.sigma.util.MaristaUtils;

public class SolicitudLogisticoDetalleService {

    private SolicitudLogisticoDetalleDAO solicitudLogisticoDetalleDAO;
    private CatalogoDAO catalogoDAO;

    //Getter and Setter
    public SolicitudLogisticoDetalleDAO getSolicitudLogisticoDetalleDAO() {
        return solicitudLogisticoDetalleDAO;
    }

    public void setSolicitudLogisticoDetalleDAO(SolicitudLogisticoDetalleDAO solicitudLogisticoDetalleDAO) {
        this.solicitudLogisticoDetalleDAO = solicitudLogisticoDetalleDAO;
    }

    //Metodos Lógica de Negocio
    @Transactional
    public void insertar(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
//        for (SolicitudLogDetalleBean listaDetalle1 : listaDetalle) {
//            listaDetalle1.setIdRequerimiento(idRequerimiento);
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            listaDetalle1.getSolicitudLogisticoBean().setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
//            listaDetalle1.setCreaPor(usuarioBean.getUsuario());
//             listaDetalle1.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
//             listaDetalle1.getCatalogoBean().getInventarioActivoBean().setStockActual(solicitudLogDetalleBean.getCatalogoBean().getInventarioActivoBean().getStockActual());
////            listaDetalle1.getConceptoUniNegBean().setConceptoBean(listaDetalle1.getConceptoUniNegBean().getConceptoBean());
////            listaDetalle1.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaDBean(listaDetalle1.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
////            listaDetalle1.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaHBean(listaDetalle1.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
        solicitudLogisticoDetalleDAO.insertar(solicitudLogDetalleBean);
//            catalogoDAO.actualizarPrecioRef(solicitudLogDetalleBean.getCatalogoBean());

//        }  
    }

    public void insertarServicio(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        solicitudLogisticoDetalleDAO.insertarServicio(solicitudLogDetalleBean);
    }

    @Transactional
    public void eliminar(Integer idRequerimiento) throws Exception {
        solicitudLogisticoDetalleDAO.eliminar(idRequerimiento);
    }

    public List<SolicitudLogDetalleBean> obtenerPorSolicitud(Integer idRequerimiento, String uniNeg) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerPorSolicitud(idRequerimiento, uniNeg);
    }

    public CatalogoDAO getCatalogoDAO() {
        return catalogoDAO;
    }

    public void setCatalogoDAO(CatalogoDAO catalogoDAO) {
        this.catalogoDAO = catalogoDAO;
    }

    public Integer obtenerUltimo(Integer idRequerimiento) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerUltimo(idRequerimiento);
    }

    public List<SolicitudLogDetalleBean> obtenerPorRequerimiento(Integer idRequerimiento) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerPorRequerimiento(idRequerimiento);
    }

    public List<SolicitudLogDetalleBean> obtenerPorDetRequerimiento(Integer idDetRequerimiento) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerPorDetRequerimiento(idDetRequerimiento);
    }

    public void modificarPrecioDetalle(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        solicitudLogisticoDetalleDAO.modificarPrecioDetalle(solicitudLogDetalleBean);
    }

    public void modificarPrecioDetalles(Double montoRef, Integer idDetRequerimiento, Integer idCatalogo) throws Exception {
        solicitudLogisticoDetalleDAO.modificarPrecioDetalles(montoRef, idDetRequerimiento, idCatalogo);
    }

    public Integer obtenerCantidadEntregada(String uniNeg) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerCantidadEntregada(uniNeg);
    }

    @Transactional
    public List<SolicitudLogDetalleBean> execProInventario(CatalogoBean catalogoBean) throws Exception {
        return solicitudLogisticoDetalleDAO.execProInventario(catalogoBean);
    }

    @Transactional
    public List<SolicitudLogDetalleBean> execProInventarioTrans(CatalogoBean catalogoBean) throws Exception {
        return solicitudLogisticoDetalleDAO.execProInventarioTrans(catalogoBean);
    }

    public SolicitudLogDetalleBean ejecutarDetalleProcedure(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        return solicitudLogisticoDetalleDAO.ejecutarDetalleProcedure(solicitudLogDetalleBean);
    }

    public List<SolicitudLogisticoRepBean> obtenerSolicitudPDF(Integer idRequerimiento, String uniNeg) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerSolicitudPDF(idRequerimiento, uniNeg);
    }

    public List<SolicitudLogisticoRepBean> obtenerSolicitudTransportePDF(Integer idRequerimiento, String uniNeg) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerSolicitudTransportePDF(idRequerimiento, uniNeg);
    }

    public void modificarFlgCompra(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        solicitudLogisticoDetalleDAO.modificarFlgCompra(solicitudLogDetalleBean);
    }

    public List<SolicitudLogDetalleBean> obtenerPorSolicitudCompra(Integer idRequerimiento, String uniNeg) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerPorSolicitudCompra(idRequerimiento, uniNeg);
    }

    public void modificarFlgCompraTotal(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        solicitudLogisticoDetalleDAO.modificarFlgCompraTotal(solicitudLogDetalleBean);
    }

    public SolicitudLogDetalleBean obtenerPorSolicitudBean(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception {
        return solicitudLogisticoDetalleDAO.obtenerPorSolicitudBean(solicitudLogDetalleBean);
    }

}
