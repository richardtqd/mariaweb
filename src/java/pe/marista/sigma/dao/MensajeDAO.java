package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.MensajeBean;
import pe.marista.sigma.bean.PersonalBean;

/**
 *
 * @author Administrador
 */
public interface MensajeDAO {

    public List<MensajeBean> obtenerMensajePorOwner(MensajeBean mensajeBean) throws Exception;

    public List<MensajeBean> obtenerMensajePorOwnerRecibidos(MensajeBean mensajeBean) throws Exception;

    public List<MensajeBean> obtenerMensajePorOwnerAtendidos(MensajeBean mensajeBean) throws Exception;
    
    public List<MensajeBean> obtenerMensajePorFiltro(MensajeBean mensajeBean) throws Exception;

    public List<MensajeBean> enviarMensajePapelera(MensajeBean mensajeBean) throws Exception;
  
    public void autorizarMensajeSolCajaCh(MensajeBean mensajeBean) throws Exception; //AUTORIZAR O DESAUTORIZAR SOLICITUD
    
    public void autorizarMensajeRegistroCompra(MensajeBean mensajeBean) throws Exception; //AUTORIZAR O DESAUTORIZAR REGISTRO COMPRA
    
    public void autorizarMensajeFacturaCompra(MensajeBean mensajeBean) throws Exception; //AUTORIZAR O DESAUTORIZAR FACTURA COMPRA
    
    public void autorizarMensajeSolLog(MensajeBean mensajeBean) throws Exception; //AUTORIZAR O DESAUTORIZAR REGISTRO COMPRA

//    public void desautorizarMensajeSolCajaCh(MensajeBean mensajeBean) throws Exception;

    public void actualizarFechaAccionStatusMsje(MensajeBean mensajeBean) throws Exception;
    
    public void eliminarMensaje(MensajeBean mensajeBean) throws Exception;
    
    public void cambiarStatusMsjeAnulado(MensajeBean mensajeBean) throws Exception;

    public MensajeBean obtenerMensajePorId(MensajeBean mensajeBean) throws Exception;

    public MensajeBean obtenerMensajePorIdTabla(MensajeBean mensajeBean) throws Exception;
    
     public void llamarAlDuende(MensajeBean mensajeBean) throws Exception;  
      
     public List<MensajeBean> obtenerTodosLosMensajesPorPersonal(PersonalBean personalBean) throws Exception;
     
     public List<MensajeBean> obtenerTodosLosMensajes(PersonalBean personalBean) throws Exception;
     
     //Para ver todas las solicitudes 
     public String obtenerMensajePorFiltroTodos(@Param("uniNeg") String uniNeg) throws Exception;
}
