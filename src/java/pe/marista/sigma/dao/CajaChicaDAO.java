package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CajaChicaSaldoBean;

/**
 *
 * @author Administrador
 */
public interface CajaChicaDAO {

    public void insertarCajaChica(CajaChicaBean cajaChicaBean) throws Exception;

    public void modificarCajaChica(CajaChicaBean cajaChicaBean) throws Exception;

    public void modificarCajaChicaCantidadades(CajaChicaBean cajaChicaBean) throws Exception;

    public void eliminarCajaChica(CajaChicaBean cajaChicaBean) throws Exception;

    public CajaChicaBean obtenerCajaChicaPorId(CajaChicaBean cajaChicaBean) throws Exception;

    public List<CajaChicaBean> obtenerCajaChica(CajaChicaBean cajaChicaBean) throws Exception;

    public List<CajaChicaBean> obtenerCajaChicaPorFiltro(CajaChicaBean cajaChicaBean) throws Exception;

//    public void abrirCaja(CajaChicaBean cajaChicaBean) throws Exception;
    public void cerrarCaja(CajaChicaBean cajaChicaBean) throws Exception;

    public void insertarCajaChicaSaldo(CajaChicaSaldoBean cajaChicaSaldoBean) throws Exception;

    public void modificarCajaChicaSaldo(CajaChicaSaldoBean cajaChicaSaldoBean) throws Exception;

    public CajaChicaSaldoBean obtenerCajaChicaSaldoPorCCH(CajaChicaSaldoBean cajaChicaSaldoBean) throws Exception;

    public List<CajaChicaBean> obtenerCajaChicaAbierto(CajaChicaBean cajaChicaBean) throws Exception;

    public List<CajaChicaBean> obtenerUltimaCajaChicaCerrada(CajaChicaBean cajaChicaBean) throws Exception;

    //Reposición

    public void modificarIdSolRep(CajaChicaBean cajaChicaBean) throws Exception;

    //Anulacion Caja Chica
    public void modificarCajaChicaAnulacionDolares(@Param("modiPor") String modiPor, @Param("uniNeg") String uniNeg, @Param("idCajaChica") Integer idCajaChica, @Param("utilizado") Double utilizado, @Param("saldo") Double saldo) throws Exception;

    public void modificarCajaChicaAnulacionSoles(@Param("modiPor") String modiPor, @Param("uniNeg") String uniNeg, @Param("idCajaChica") Integer idCajaChica, @Param("utilizado") Double utilizado, @Param("saldo") Double saldo) throws Exception;

    public void modificarCajaChicaSaldoAnulacion(@Param("modiPor") String modiPor, @Param("uniNeg") String uniNeg, @Param("idCajaChica") Integer idCajaChica, @Param("importe") Double importe, @Param("idTipoMoneda") Integer idTipoMoneda) throws Exception;
}
