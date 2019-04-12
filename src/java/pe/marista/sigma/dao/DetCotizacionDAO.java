/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.reporte.DetCotizacionRepBean;

/**
 *
 * @author Administrador
 */
public interface DetCotizacionDAO {

    public List<DetCotizacionBean> obtenerPorOrden(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetCotizacionRepBean> obtenerPorOrdenPrimero(@Param("idCotizacion") Integer idCotizacion, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerUltimo(@Param("idCotizacion") Integer idCotizacion, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetCotizacionBean> obtenerListaPorId(Integer idDetCotizacion) throws Exception;

    public List<DetCotizacionBean> obtenerTodos(String uniNeg) throws Exception;

    public void insertar(DetCotizacionBean detCotizacionBean) throws Exception;

    public void eliminar(CotizacionBean cotizacionBean) throws Exception;

    public void validarRecepcionCompra(DetCotizacionBean detCotizacionBean) throws Exception;

    public DetCotizacionBean obtenerPorId(DetCotizacionBean detCotizacionBean) throws Exception;

    public List<DetCotizacionBean> obtenerListaPorIdSolicitud(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetCotizacionBean> obtenerListaPorIdSolicitud2(DetCotizacionBean detCotizacionBean) throws Exception;

    public List<DetCotizacionBean> obtenerTodosM(DetCotizacionBean detCotizacionBean) throws Exception;

    public Integer obtenerRequerimiento(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerUltimoCoti(@Param("idCotizacion") Integer idCotizacion, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetCotizacionBean> obtenerTodosGeneral(DetCotizacionBean detCotizacionBean) throws Exception;

    public List<DetCotizacionBean> obtenerPorOrdenSoli(@Param("idCotizacion") Integer idCotizacion, @Param("uniNeg") String uniNeg) throws Exception;
}
