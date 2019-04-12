package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentaBancoBean;

/**
 *
 * @author Administrador
 */
public interface CuentaBancoDAO {

    public void insertarCuentaBanco(CuentaBancoBean cuentaBancoBean) throws Exception;

    public void modificarCuentaBanco(CuentaBancoBean cuentaBancoBean) throws Exception;

    public void eliminarCuentaBanco(CuentaBancoBean cuentaBancoBean) throws Exception;

    public CuentaBancoBean obtenerCuentaBancoPorId(CuentaBancoBean cuentaBancoBean) throws Exception;

    public CuentaBancoBean obtenerCuentaBancoPorNumCta(CuentaBancoBean cuentaBancoBean) throws Exception;

    public List<CuentaBancoBean> obtenerCuentaPorUniNeg(String cuentaBancoBean) throws Exception;
    
    public List<CuentaBancoBean> obtenerCuentaPorCongregacion(String cuentaBancoBean) throws Exception;

    public List<CuentaBancoBean> obtenerBancoPorTipMoneda(@Param("idTipoCodigo") String idTipoCodigo, @Param("codigo") String codigo, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CuentaBancoBean> obtenerBancoPorTipMonedaBcoColegio(@Param("idTipoCodigo") String idTipoCodigo, @Param("codigo") String codigo, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CuentaBancoBean> obtenerBancoPorTipMonedaYBanco(@Param("idTipoCodigo") String idTipoCodigo, @Param("codigo") String codigo, @Param("uniNeg") String uniNeg, @Param("rucBanco") String rucBanco) throws Exception;

    public List<CuentaBancoBean> obtenerBancoPorTipMonedaYBancoFlgCobranza(@Param("idTipoCodigo") String idTipoCodigo, @Param("codigo") String codigo, @Param("uniNeg") String uniNeg, @Param("rucBanco") String rucBanco, @Param("flgCobranza") Boolean flgCobranza) throws Exception;

    public List<CuentaBancoBean> obtenerCuentaPorFiltro(CuentaBancoBean cuentaBancoBean) throws Exception;

    public List<CuentaBancoBean> obtenerCuentaPorTipo(CuentaBancoBean cuentaBancoBean) throws Exception;

    public List<CuentaBancoBean> obtenerPorRuc(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idTipoMoneda") Integer idTipoMoneda) throws Exception;

    public List<CuentaBancoBean> obtenerPorRucRecauda(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idTipoMoneda") Integer idTipoMoneda) throws Exception;
    
    public CuentaBancoBean obtenerPorRucRecaudaVer2(@Param("uniNeg") String uniNeg,@Param("idTipoMoneda") Integer idTipoMoneda) throws Exception;

    public String obtenerRucEntidad(@Param("uniNeg") String uniNeg) throws Exception;

//    public List<CuentaBancoBean> obtenerBancoPorTipMoneda(@Param("idTipoCodigo") String idTipoCodigo, @Param("codigo") String codigo, @Param("uniNeg") String uniNeg) throws Exception;
}
