package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CajaChicaMovBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.reporte.CajaChMovRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovCRRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovCentroRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovSoliRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovilidadRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovilidadSubRepBean;
import pe.marista.sigma.bean.reporte.CrDetalladitoRepBean;

/**
 *
 * @author Administrador
 */
public interface CajaChicaMovDAO {

    public void insertarCajaChicaMov(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    public void modificarCajaChicaMov(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    public void eliminarCajaChicaMov(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    public void cambiarEstadoSolicitudCCh(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<CajaChicaMovBean> obtenerCajaChicaMovPorCCH(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    public CajaChicaMovBean obtenerCajaChicaMovPorId(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    public List<CajaChicaMovBean> obtenerCajaChicaMovPorFiltro(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    public void cambiarEstadoRendicionMov(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    public void actualizarIdDevolucion(CajaChicaMovBean cajaChicaMovBean) throws Exception;

    //Reportes

    public List<CajaChicaMovRepBean> obtenerCajaChicaMovRep(CajaChicaBean cajaChicaBean) throws Exception;

    public List<CajaChMovRepBean> obtenerCajaChicaMovRepNewFor(CajaChicaBean cajaChicaBean) throws Exception;
    
    public List<CajaChMovRepBean> obtenerCajaChicaDetalle(@Param("uniNeg") String uniNeg, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh) throws Exception;

    public List<CajaChicaMovRepBean> obtenerCajaChicaMovRepPorIdDev(Integer idCajaChicaMov) throws Exception;
    
    public void modificarCajaChicaMovAnulacion(@Param("uniNeg") String uniNeg, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh,@Param("modiPor") String modiPor,@Param("motivo") String motivo) throws Exception;
    
    public List<CajaChicaMovCRRepBean> obtenerCajaChicaCentros(@Param("uniNeg") String uniNeg, @Param("idCajaChica") Integer idCajaChica) throws Exception;
    
    public List<CajaChicaMovSoliRepBean> obtenerCajaChicaSoliCentros(@Param("uniNeg") String uniNeg, @Param("idCajaChica") Integer idCajaChica) throws Exception;
    
    public List<CajaChicaMovCentroRepBean> obtenerCajaChicaCRCentros(@Param("uniNeg") String uniNeg, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh) throws Exception;
    
    public List<CrDetalladitoRepBean> obtenerCRDetalladito(@Param("uniNeg") String uniNeg, @Param("idCajaChica") Integer idCajaChica) throws Exception;
    public List<CajaChicaMovilidadSubRepBean> obtenerCajaChicaMovDetalle(@Param("uniNeg") String uniNeg, @Param("idPersonal") Integer idPersonal, @Param("idCajaChica") Integer idCajaChica) throws Exception;
    public List<CajaChicaMovilidadRepBean> obtenerCajaChicaMovCabecera(@Param("uniNeg") String uniNeg, @Param("idCajaChica") Integer idCajaChica ) throws Exception;
}
