/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.reporte.ChequesEmitidosLPMRepBean;
import pe.marista.sigma.bean.reporte.DetDetDocEgresoRepBean;
import pe.marista.sigma.bean.reporte.DetDocEgresoRepBean;
import pe.marista.sigma.bean.reporte.DocEgresoRepBean;
import pe.marista.sigma.bean.reporte.PagosEmitidosRepBean;

/**
 *
 * @author MS002
 */
public interface DocEgresoDAO {

    public void insertarDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    public void modificarDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    public void flgAnuladoDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    public void eliminarDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    public void eliminarDocEgresoCheque(DocEgresoBean docEgresoBean) throws Exception;

    public DocEgresoBean obtenerDocEgresoPorId(DocEgresoBean docEgresoBean) throws Exception;

    public DocEgresoBean obtenerDocEgresoCheque(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerFacturaPorNumCheq(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoPorIdDistinct(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoRegistroCompraDistinct(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoUniNeg(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoPorFiltro(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoChequePorFiltro(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoPorFiltroARend(DocEgresoBean docEgresoBean) throws Exception;

    public Integer obtenerUltimoDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoPorFiltroDistinct(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerDocEgresoPorFiltroDistinctNoAnulados(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoBean> obtenerPorNroDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    ///////////////////Reportes//////////////////////////////////////////////////
    public List<DocEgresoRepBean> obtenerDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoRepBean> obtenerDocEgreso2(DocEgresoBean docEgresoBean) throws Exception;

    public List<DetDocEgresoRepBean> obtenerDetalleDocEgreso(@Param("nroDocEgreso") Integer nroDocEgreso, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetDocEgresoRepBean> obtenerDetalleFacturaDocEgreso(@Param("nroDocEgreso") Integer nroDocEgreso, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetDetDocEgresoRepBean> obtenerDetalleDetalleDocEgreso(@Param("nroDocEgreso") Integer nroDocEgreso, @Param("uniNeg") String uniNeg, @Param("id") Integer id, @Param("detra") Integer detra, @Param("gara") Integer gara) throws Exception;

    public List<DetDetDocEgresoRepBean> obtenerDetalleDetalleFacturaDocEgreso(@Param("nroDocEgreso") Integer nroDocEgreso, @Param("uniNeg") String uniNeg, @Param("id") Integer id, @Param("detra") Integer detra, @Param("cuenta") String cuenta) throws Exception;

    public String convertirAletras(BigDecimal monto) throws Exception;

    //Devolucion a REdnir
    public List<DocEgresoBean> obtenerPorIdPersonal(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoRepBean> obtenerFiltroDocEgresoCheque(DocEgresoRepBean DocEgresoRepBean) throws Exception;

    public void cambiarEstadoRendicionDoc(DocEgresoBean docEgresoBean) throws Exception;

    public DocEgresoBean obtenerDocEgresoPorSol(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception;

    public List<PagosEmitidosRepBean> obtenerFiltrosPagosEmitidosRep(PagosEmitidosRepBean pagosEmitidosRepBean) throws Exception;

    public String obtenerActualMas1PorTipoPago(@Param("tipoPago") String tipoPago, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarFechaDocEgreso(DocEgresoBean docEgresoBean) throws Exception;

    public DocEgresoBean obtenerDocEgresoPorCheque(DocEgresoBean docEgresoBean) throws Exception;

    public List<DocEgresoRepBean> obtenerFiltroDocEgresoChequeSolesLPM(DocEgresoRepBean DocEgresoRepBean) throws Exception;

    public List<DocEgresoRepBean> obtenerFiltroDocEgresoChequeDolesLPM(DocEgresoRepBean DocEgresoRepBean) throws Exception;

    public List<DocEgresoBean> obtenerListaDocEgresoDetalle(DocEgresoBean docEgresoBean) throws Exception;

    //Cheques formato LPM
    public List<ChequesEmitidosLPMRepBean> obtenerChequesLPMCabecera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("tipoMoneda") Integer tipoMoneda, @Param("tipoModoPago") Integer tipoModoPago) throws Exception;

    public List<ChequesEmitidosLPMRepBean> obtenerChequesLPMDetalle(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("numCheque") String numCheque) throws Exception;

    public List<ChequesEmitidosLPMRepBean> obtenerChequesLPMSubDetalle(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("numCheque") String numCheque) throws Exception;
}
