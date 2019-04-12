/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DetEsquelaBean;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.reporte.MasivoCartaUnoBean;
import pe.marista.sigma.bean.reporte.NotiMasivaRepBean;
import pe.marista.sigma.bean.reporte.SubReporteMasivoCartaUno;

/**
 *
 * @author MS002
 */
public interface DetEsquelaDAO {

    public void insertarDetEsquela(DetEsquelaBean detEsquelaBean) throws Exception;

    public void modificarDetEsquela(DetEsquelaBean detEsquelaBean) throws Exception;

    public void modificarDetMensaje(DetEsquelaBean detEsquelaBean) throws Exception;

    public void eliminarDetEsquela(DetEsquelaBean detEsquelaBean) throws Exception;

    public DetEsquelaBean obtenerPorId(Integer idDetEsquela) throws Exception;

    public DetEsquelaBean obtenerPorTitulo(@Param("titulo") String titulo) throws Exception;

    public Integer obtenerTotalPorDia(String uniNeg) throws Exception;

    public Integer obtenerMaxId(String uniNeg) throws Exception;

    public List<DetEsquelaBean> obtenerPorFecha(@Param("fecha") String fecha, @Param("uniNeg") String uniNeg, @Param("status") Integer status) throws Exception;

    public List<DetEsquelaBean> obtenerPorFechaMen(@Param("fecha") String fecha, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetEsquelaBean> obtenerTodos(String uniNeg) throws Exception;

    public List<DetEsquelaBean> obtenerDetalles(String uniNeg) throws Exception;

    public List<DetEsquelaBean> obtenerListaOk(@Param("fecha") String fecha, @Param("uniNeg") String uniNeg, @Param("idEsquela") Integer idEsquela) throws Exception;

    public List<DetEsquelaBean> obtenerListaFail(@Param("creaFecha") Date creaFecha, @Param("uniNeg") String uniNeg, @Param("idEsquela") Integer idEsquela) throws Exception;

    public List<MasivoCartaUnoBean> obtenerListaEsquelaRep(DetEsquelaBean detEsquelaBean) throws Exception;

    public List<SubReporteMasivoCartaUno> obtenerListaDeuda(@Param("uniNeg") String uniNeg, @Param("idEstudiante") String idEstudiante, @Param("anio") Integer anio, @Param("listaMeses") Integer[] listaMeses) throws Exception;

    //NUEVOS METODOS
    public void insertarEnvioMasivo(DetEsquelaBean detEsquelaBean) throws Exception;

    public List<DetEsquelaBean> obtenerMensajesPorTipo(DetEsquelaBean detEsquelaBean) throws Exception;

    public Integer obtenerMaxEsquela(String uniNeg) throws Exception;

    //NOTIFICACION MASIVA
    public List<NotiMasivaRepBean> obtenerNotiMasiva(NotiMasivaRepBean notiMasivaRepBean) throws Exception;

    public Object execProEsquelaMasivo(@Param("unineg") String unineg, @Param("idestudiante") String idestudiante, @Param("anio") Integer anio, @Param("mes") Integer mes,@Param("mensaje") String mensaje) throws Exception;

    public void modificarEstadoBorrador(DetEsquelaBean detEsquelaBean) throws Exception;
    
}
