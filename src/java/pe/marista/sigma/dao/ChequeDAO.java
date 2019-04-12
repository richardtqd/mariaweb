package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ChequeBean;

public interface ChequeDAO {

    public void insertarCheque(ChequeBean chequeBean) throws Exception;

    public void modificarCheque(ChequeBean chequeBean) throws Exception;

    public void eliminarCheque(ChequeBean chequeBean) throws Exception;

    public ChequeBean obtenerChequePorId(ChequeBean chequeBean) throws Exception;

    public List<ChequeBean> obtenerChequePorFiltro(ChequeBean chequeBean) throws Exception;

    public List<ChequeBean> obtenerChequeActivos(ChequeBean chequeBean) throws Exception;

    public ChequeBean obtenerUltimoChequeMasUno(@Param("idTipoMoneda") Integer idTipoMoneda, @Param("impresora") String impresora, @Param("uniNeg") String uniNeg);

    public ChequeBean obtenerUltimoChequeCorrelativo(@Param("idTipoMoneda") Integer idTipoMoneda, @Param("impresora") String impresora, @Param("uniNeg") String uniNeg);   
    
    public void aumentarSecuenciaCheque(ChequeBean chequeBean) throws Exception;

}
