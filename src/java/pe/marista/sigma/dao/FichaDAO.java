package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.FichaBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;

public interface FichaDAO {

    public List<FichaBean> obtener(FichaBean fichaBean) throws Exception;

    public List<FichaBean> obtenerPorId(FichaBean fichaBean) throws Exception;

    public Integer obtenerMaxNro(FichaBean fichaBean) throws Exception;

    public Integer obtenerNroDocFicha(FichaBean fichaBean) throws Exception;
    
    public Integer obtenerMaxNroDocFicha(FichaBean fichaBean) throws Exception;

    public void insertar(FichaBean fichaBean) throws Exception;

    public void actualizar(FichaBean fichaBean) throws Exception;

    public void eliminar(FichaBean fichaBean) throws Exception;

    //FILTROS DE FICHAS
    public List<FichaBean> obtenerFichaPorPaganteEst(FichaBean fichaBean) throws Exception;

    public List<FichaBean> obtenerFichaPorPagantePer(FichaBean fichaBean) throws Exception;

    public List<FichaBean> obtenerFichaPorPaganteExt(FichaBean fichaBean) throws Exception;

    public List<FichaBean> obtenerFichaPorPaganteEnt(FichaBean fichaBean) throws Exception;

    //PRO_RECUPERACION_BINGO
    public Object execProCtaCteBingo(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("modiPor") String modiPor) throws Exception;

    //FILTRO DE FICHAS PAGADAS
    public List<FichaBean> filtrarFichasPagadas(FichaBean fichaBean) throws Exception;

    //VALIDAR NRO BINGO
    public Integer obtenerNroFicha(FichaBean fichaBean) throws Exception;

    //OBTENER NRO POR ID
    public Integer obtenerIdNroFicha(FichaBean fichaBean) throws Exception;

    //MODIFICAR ESTADO FICHA
    public void modificarEstado(FichaBean fichaBean) throws Exception;

    //FILTRO POR ESTADO DE FICHA
    public List<FichaBean> obtenerPorTipoEstado(FichaBean fichaBean) throws Exception;

    //OBTENER IMPRESORA CAJERO
    public List<ImpresoraBean> obtenerImpresoraCajero(CajeroCajaBean cajeroCajaBean) throws Exception;

    //CONSULTA DE IMPRESORAS
    public List<CodigoBean> obtenerTipDocumentoPorImpresora(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idCaja") Integer idCaja) throws Exception;

    public List<CodigoBean> obtenerTipDocumentoPorImpresoraComprobante(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idCaja") Integer idCaja, @Param("tipoDoc") String tipoDoc) throws Exception;

    public ImpresoraCajaBean obtenerDetalleTipoDoc(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idTipoDoc") Integer idTipoDoc, @Param("idCaja") Integer idCaja) throws Exception;

    //MODIFICAR FICHA PAGADA
    public void modificarFichaPagada(FichaBean fichaBean) throws Exception;

    //GENERAR COMPROBANTE DE PAGO
    public List<CobranzaValoradoRepBean> generarReciboValorado(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    //OBTENER NUMERO DE FICHA
    public Integer obtenerMaxIdFicha(String uniNeg) throws Exception;

    //MODIFICAR NUMERO DE DOC IMPRESORA
    public void modificarImpresoraActual(ImpresoraBean impresoraBean) throws Exception;

    //OBTENER POR ID FICHA
    public FichaBean obtenerPorIdFicha(FichaBean fichaBean) throws Exception;

    public void eliminarFicha(FichaBean fichaBean) throws Exception;

    //HISTORIAL DE FICHAS PAGADAS
    public List<FichaBean> obtenerHistorialCarga(FichaBean fichaBean) throws Exception;

    //FILTRO DE FICHAS POR FAMILIA
    public List<FichaBean> filtrarFichasPagadasFamilia(FichaBean fichaBean) throws Exception;

    //LISTA DE FICHAS POR OBJETO
    public List<FichaBean> obtenerFichaPorPaganteObj(FichaBean fichaBean) throws Exception;

    //LISTA DE FICHAS PAGADAS POR PAGANTE
    public List<FichaBean> obtenerFichaPorPagantePagado(FichaBean fichaBean) throws Exception;

    //NUMERO DE FICHAS DONADOS
    public Integer obtenerNumFichaDon(FichaBean fichaBean) throws Exception;

    //Consulta de Fichas Especiales
    public List<FichaBean> obtenerPorNroFicha(FichaBean fichaBean) throws Exception;

    //LISTA DE PAGOS POR OBJETO
    public List<FichaBean> filtrarFichasPagadasObj(FichaBean fichaBean) throws Exception;

    //LISTA DE OBJETOS PAGANTE
    public List<FichaBean> obtenerFichaPorObjPagado(FichaBean fichaBean) throws Exception;

    //OBTENER POR NRO FICHA
    public FichaBean obtenerPorNroFichaEspecial(FichaBean fichaBean) throws Exception;

}
