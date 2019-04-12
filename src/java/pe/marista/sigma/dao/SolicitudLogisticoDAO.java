package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;

public interface SolicitudLogisticoDAO {

    public List<SolicitudLogisticoBean> obtenerTodosSol() throws Exception;

    public List<SolicitudLogisticoBean> obtenerTodosSolDes(SolicitudLogisticoBean bean) throws Exception;

    public List<SolicitudLogisticoBean> obtenerTodosM(SolicitudLogisticoBean bean) throws Exception;
    
    public List<SolicitudLogisticoBean> obtenerTodosMDespacho(SolicitudLogisticoBean bean) throws Exception;

    public List<SolicitudLogisticoBean> obtenerTodosSolicitud(SolicitudLogisticoBean bean) throws Exception;

    public List<SolicitudLogisticoBean> obtenerTodosCat(SolicitudLogisticoBean bean) throws Exception;

    public List<SolicitudLogisticoBean> obtenerTodosAprob(SolicitudLogisticoBean bean) throws Exception;

    public List<SolicitudLogisticoBean> obtenerTodosCompra(SolicitudLogisticoBean bean) throws Exception;

    public List<SolicitudLogisticoBean> obtenerTodos() throws Exception;

    public SolicitudLogisticoBean obtenerPorId(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public List<SolicitudLogisticoBean> obtenerPorFiltro(SolicitudLogisticoBean bean) throws Exception;

    public void insertar(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;
    public void insertarServicio(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    public void actualizar(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    public void actualizarFechaAprobacion(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    // mis solicitudes mensaje lista se SolicitudLogisticoBean
    public List<SolicitudLogisticoBean> obtenerPorIdSoli(PersonalBean personalBean) throws Exception;
//

    public void anularSolicitudLog(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    public void cambiarEstadoSolicitudLog(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;//cambia el estado y el monto aprobado

    public Object llamarAutorizadores(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    public void insertarDetRequerimientoCR(DetRequerimientoCRBean detRequerimientoCRBean) throws Exception;
   
    //CR
    public List<DetRequerimientoCRBean> ObtenerPorIdCR(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    public List<DetRequerimientoCRBean> ObtenerPorIdCRDis(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    public void ObtenerPorIdCRes(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    //cambia estado para OC
    public void cambiarEstadoSolicitudLogComprado(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;
    
    public void cambiarEstadoSolicitudLogParcial(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    //CR Registro de Compra
    public List<DetRequerimientoCRBean> obtenerCRRegistro(@Param("idRegistroCompra") Integer idOrdenCompra, @Param("uniNeg") String uniNeg) throws Exception;
    public List<CentroResponsabilidadBean> obtenerCRInRegistro(@Param("idRegistroCompra") Integer idOrdenCompra, @Param("uniNeg") String uniNeg) throws Exception;
    public List<DetRequerimientoCRBean> obtenerCROrden(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;
    public List<CentroResponsabilidadBean> obtenerCRInOrden(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;
   
    // requerimiento
    public void elimnarRequerimientoCrPorReq(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public String obtenerUltimoSoli(String uniNeg) throws Exception;
    public List<SolicitudLogisticoBean> obtenerCerosSol(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;
}
