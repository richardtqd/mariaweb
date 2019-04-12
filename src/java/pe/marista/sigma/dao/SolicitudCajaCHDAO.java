package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;

/**
 *
 * @author Administrador
 */
public interface SolicitudCajaCHDAO {

    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCH() throws Exception;//

    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCHBeanPorUniNeg(String uniNeg) throws Exception;//

    public void insertarSolicitudCajaCH1(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//

    public SolicitudCajaCHBean obtenerTipoSolicitanteResCheque(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerTipoDistribucion(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarSolicitudCajaCH(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//

    public void modificarMotivo(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//
    
    public void modificarTipoSol(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//

    public void eliminarSolicitudCajaCH(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//

    public SolicitudCajaCHBean obtenerSolicitudCajaCHBeanPorId(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//

    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCHBeanPorPersonal(PersonalBean personalBean) throws Exception;//

    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCHBeanPorGestor(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroPersonal(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltro(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroMenorA(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroGestor(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroGestorPorActividad(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroGestorPorActividadAdmi(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    //Aprobacion 
//    public void autorizarSolicitudCCH(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;
    public Object llamarProGetAutorizadores(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public void anularSolicitudCCH(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public void cambiarEstadoSolicitudMontoAprobadoCCh(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//cambia el estado y el monto aprobado

    public void cambiarEstadoPagadoSolicitudCCh(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;//cambia estado a pagado

    public void insertarDetSolicitudCajaChCR(DetSolicitudCajaChCRBean detSolicitudCajaChCRBean) throws Exception;

    public void eliminarDetSolicitudCajaChCR(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetSolicitudCajaChCRBean> obtenerDetSolcitudCajaChCRPorSol(DetSolicitudCajaChCRBean detSolicitudCajaChCRBean) throws Exception;

    public List<DetSolicitudCajaChCRBean> obtenerDetSolcitudCajaChCRPorSolCaj(SolicitudCajaCHBean solicitudCajaChBean) throws Exception;

    public String obtenerDetSolcitudCajaChCRNotaCredito(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    // mis solicitudes, mensaje, lista de CajaChica para obtener la lista de operaciones realizadas
    public List<SolicitudCajaCHBean> obtenerPorIdCaja(PersonalBean bean) throws Exception;

    //reNDICION DOCEGRESO
    public void modificarTipoEstRend(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<SolicitudCajaCHBean> obtenerPorTipoEstRend(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public SolicitudCajaCHBean obtenerPorTipoEstRendPorId(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarDetSolicitudCrAnulacion(@Param("uniNeg") String uniNeg, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("modiPor") String modiPor) throws Exception;

    public void modificarSolicitudAnulacion(@Param("uniNeg") String uniNeg, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("modiPor") String modiPor, @Param("motivo") String motivo) throws Exception;
    
    public void modificarDetalleCrValorNoC(@Param("valor") Double valor, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg, @Param("cr") Integer cr) throws Exception;
}
