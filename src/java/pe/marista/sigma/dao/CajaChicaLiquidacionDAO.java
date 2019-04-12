package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CajaChicaLiquidacionBean;
import pe.marista.sigma.bean.reporte.LiquidacionRepBean;

/**
 *
 * @author Administrador
 */
public interface CajaChicaLiquidacionDAO {

    public void insertarCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception;
 
    public void modificarCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception;

    public void modificarDevCajaChicaLiquidacion(@Param("uniNeg") String uniNeg, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("montoDevuelto") Double montoDevuelto) throws Exception;

    public void eliminarCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception;

    public CajaChicaLiquidacionBean obtenerCajaChicaLiquidacionPorId(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception;

    public List<CajaChicaLiquidacionBean> obtenerCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception;

    public List<CajaChicaLiquidacionBean> obtenerCajaChicaLiquidacionPorMov(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception;

    public List<CajaChicaLiquidacionBean> obtenerCajaChicaLiquidacionPorDoc(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception;

    public List<LiquidacionRepBean> obtenerLiquidacion(@Param("uniNeg") String uniNeg, @Param("idObjeto") Integer idObjeto) throws Exception;

    public List<LiquidacionRepBean> obtenerDetLiquidacion(@Param("uniNeg") String uniNeg, @Param("idObjeto") Integer idObjeto, @Param("idCajaChicaLiq") Integer idCajaChicaLiq) throws Exception;
    
    public void modificarCajaChicaLiquidacionAnulacion(@Param("uniNeg") String uniNeg, @Param("idSolicitudCajaCh") Integer idSolicitudCajaCh,@Param("modiPor") String modiPor) throws Exception;

}
