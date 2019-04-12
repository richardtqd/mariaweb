package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.reporte.SolicitudLogisticoRepBean;

public interface SolicitudLogisticoDetalleDAO {

    public List<SolicitudLogDetalleBean> obtenerPorSolicitud(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;
    
    public SolicitudLogDetalleBean obtenerPorSolicitudBean(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception;
    
    public List<SolicitudLogDetalleBean> obtenerPorSolicitudCompra(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

//    public void insertar(@Param("idRequerimiento") Integer idRequerimiento,@Param("listaDetalle") List<SolicitudLogDetalleBean> listaDetalle) throws Exception;
    public void insertar(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception;

    public void insertarServicio(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception;

    public void eliminar(Integer idRequerimiento) throws Exception;

    public Integer obtenerUltimo(Integer idRequerimiento) throws Exception;

    public List<SolicitudLogDetalleBean> obtenerPorRequerimiento(Integer idRequerimiento) throws Exception;

    public List<SolicitudLogDetalleBean> obtenerPorDetRequerimiento(Integer idDetRequerimiento) throws Exception;

    public void modificarPrecioDetalle(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception;
    
    public void modificarFlgCompra(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception;
    
    public void modificarFlgCompraTotal(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception;

    public void modificarPrecioDetalles(@Param("montoRef") Double montoRef, @Param("idDetRequerimiento") Integer idDetRequerimiento, @Param("idCatalogo") Integer idCatalogo) throws Exception;
    
    public List<SolicitudLogisticoRepBean> obtenerSolicitudPDF(@Param("idRequerimiento") Integer idRequerimiento,@Param("uniNeg") String  uniNeg) throws Exception;
    
    public List<SolicitudLogisticoRepBean> obtenerSolicitudTransportePDF(@Param("idRequerimiento") Integer idRequerimiento,@Param("uniNeg") String  uniNeg) throws Exception;

    public Integer obtenerCantidadEntregada(String uniNeg) throws Exception;

    public List<SolicitudLogDetalleBean> execProInventario(CatalogoBean catalogoBean) throws Exception;

    public List<SolicitudLogDetalleBean> execProInventarioTrans(CatalogoBean catalogoBean) throws Exception;
    
    public SolicitudLogDetalleBean ejecutarDetalleProcedure(SolicitudLogDetalleBean solicitudLogDetalleBean) throws Exception;

}
