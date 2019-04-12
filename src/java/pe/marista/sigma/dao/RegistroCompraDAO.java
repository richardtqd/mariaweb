package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CuentaFacturaBean;
import pe.marista.sigma.bean.DetRegistroCompraCRBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.RegistroCompraBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.reporte.RegistroCompraRepBean;

public interface RegistroCompraDAO {

    public List<RegistroCompraBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception;

    public RegistroCompraBean obtenerPorId(RegistroCompraBean registroCompraBean) throws Exception;

    public void insertar(RegistroCompraBean registroCompraBean) throws Exception;

    public void modificar(RegistroCompraBean registroCompraBean) throws Exception;

    public List<RegistroCompraBean> obtenerPorFiltroRC(RegistroCompraBean orden) throws Exception;

    public List<RegistroCompraBean> obtenerPorFiltroRCAutorizados(RegistroCompraBean orden) throws Exception;

    public List<RegistroCompraBean> obtenerTodosAutorizados(RegistroCompraBean registroCompraBean) throws Exception;

    public Object llamarAutorizadores(RegistroCompraBean registroCompraBean) throws Exception;

    public List<RegistroCompraBean> obtenerSolicitudPorFiltroGestor(RegistroCompraBean registroCompraBean) throws Exception;

    public List<RegistroCompraBean> obtenerSolicitudPorFiltroPersonal(RegistroCompraBean registroCompraBean) throws Exception;

    public void actualizarAprobacion(RegistroCompraBean registroCompraBean) throws Exception;

    public List<RegistroCompraBean> obtenerTodosAprob() throws Exception;

    //APROBACION
//    public void autorizarSolicitudRC(RegistroCompraBean registroCompraBean) throws Exception;
    public void anularSolicitudRegistro(RegistroCompraBean registroCompraBean) throws Exception;

    public void cambiarEstadoSolicitudRC(RegistroCompraBean registroCompraBean) throws Exception;//cambia el estado 

    public void cambiarEstadoPagadoSolicitudRC(RegistroCompraBean registroCompraBean) throws Exception;//cambia el estado pagado o autorizado 

    //Facturas 
    public void insertarFactura(FacturaCompraBean facturaCompraBean) throws Exception;

    public void modificarFactura(FacturaCompraBean facturaCompraBean) throws Exception;

    public List<FacturaCompraBean> obtenerTodosPorUniNegFact(String uniNeg) throws Exception;

    public List<FacturaCompraBean> obtenerFacturaAutorizadoPorFiltro(FacturaCompraBean facturaCompraBean) throws Exception;

    public FacturaCompraBean obtenerPorIdFactura(FacturaCompraBean facturaCompraBean) throws Exception;

    public FacturaCompraBean obtenerPorIdFacturaVer2(FacturaCompraBean facturaCompraBean) throws Exception;

    public FacturaCompraBean obtenerFacturaPorSerieCompleta(FacturaCompraBean facturaCompraBean) throws Exception;

    public void modificarFacturaReg(FacturaCompraBean FacturaCompraBean) throws Exception;

    //aprobacion
    public Object llamarProGetAutorizadores(FacturaCompraBean FacturaCompraBean) throws Exception;

    public Object llamarDuende() throws Exception;

    public void anularFacturaCompra(FacturaCompraBean FacturaCompraBean) throws Exception;

    public void cambiarEstadoFacturaCompra(FacturaCompraBean FacturaCompraBean) throws Exception;//cambia el estado y el monto aprobado

    public void modificarGlosa(FacturaCompraBean FacturaCompraBean) throws Exception;//cambia el estado y el monto aprobado

    public void cambiarEstadoPagadoFacturaCompra(FacturaCompraBean FacturaCompraBean) throws Exception;//cambia estado a pagado

    public void cambiarEstadoFacturaPorReg(FacturaCompraBean FacturaCompraBean) throws Exception;//cambia estado a pagado

    public void insertarDetRegistroCompraCR(DetRegistroCompraCRBean detRegistroCompraCRBean) throws Exception;

    //DetRegistroCR
    public void insertarRegistroCR(DetRegistroCompraCRBean detRegistroCompraCRBean) throws Exception;

    // mis solicitudes, mensaje, lista de factura para obtener la lista de factura realizadas
    public List<FacturaCompraBean> obtenerPorIdFact(FacturaCompraBean bean) throws Exception;

//    //CR Registro de Compra
//    public void obtenerCRRegistro(DetRequerimientoCRBean detRequerimientoCRBean) throws Exception;
    public List<DetRequerimientoCRBean> ObtenerPorIdCR(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    public List<DetRequerimientoCRBean> ObtenerPorIdCRDis(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;

    //reoporte
    public List<RegistroCompraRepBean> obtenerRegistroCompraCabecera(@Param("idRegistroCompra") Integer idRegistroCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public String obtenerUltimoRegistro(String uniNeg) throws Exception;

    //CuentaFactura
    public void insertarCuentaFact(CuentaFacturaBean cuentaFacturaBean) throws Exception;

    public void modificarCuentaFact(CuentaFacturaBean cuentaFacturaBean) throws Exception;

    public void modificarCuentaFactDsctoNotaCred(@Param("monto") Double monto, @Param("idFact") Integer idFact, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CuentaFacturaBean> obtenerCuentaFact(CuentaFacturaBean cuentaFacturaBean) throws Exception;
    
    public List<CuentaFacturaBean> obtenerCuentaFactNota(@Param("idFacturaCompra") Integer idFacturaCompra, @Param("uniNeg") String uniNeg) throws Exception;
    
    public String obtenerCuentaDistribucion(@Param("idFacturaCompra") Integer idFacturaCompra, @Param("uniNeg") String uniNeg) throws Exception;
    
    public List<CuentaFacturaBean>  obtenerCuentaDistribucionCr(@Param("idFacturaCompra") Integer idFacturaCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public List<FacturaCompraBean> obtenerFactura(FacturaCompraBean facturaCompraBean) throws Exception;
  
    public List<CuentaFacturaBean> obtenerCuentaFactPorIdFactura(@Param("idFact") Integer idFact, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CuentaFacturaBean> obtenerCuentasPorIdFactura(@Param("idFact") Integer idFact, @Param("uniNeg") String uniNeg) throws Exception;

    public void eliminarCuentaFact(@Param("idFact") Integer idFact, @Param("uniNeg") String uniNeg) throws Exception;
      //new
//     public FacturaCompraBean obtenerPorIdFacturaPorIdCompra(@Param("idRegistroCompra") Integer idRegistroCompra, @Param("uniNeg") String uniNeg) throws Exception;
    
    public void modificarCuentaFactValorNoC(@Param("valor") Double valor, @Param("idFact") Integer idFact, @Param("uniNeg") String uniNeg, @Param("cr") Integer cr) throws Exception;
}
