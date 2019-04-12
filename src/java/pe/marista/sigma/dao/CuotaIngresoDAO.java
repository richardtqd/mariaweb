/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CajaCuotaIngresoBean;
import pe.marista.sigma.bean.CuotaIngresoBean;
import pe.marista.sigma.bean.reporte.CuotaIngresoRepBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;

/**
 *
 * @author Administrador
 */
public interface CuotaIngresoDAO {

    public void insertarCajaCuotaIngreso(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception;

    public void insertarCuotaIngreso(CuotaIngresoBean cuotaIngresoBean) throws Exception;

    public void modificarCierre(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception;
    
    public void cambioAnulado(CuotaIngresoBean CuotaIngresoBean) throws Exception;

    public void modificarMontoPorCaja(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception;

    public List<CajaCuotaIngresoBean> obtenerListaCuotaIngreso(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception;

    public Integer obtenerMaxCaja(String uniNeg) throws Exception;

    public CajaCuotaIngresoBean obtenerCajaAbierta(CajaCuotaIngresoBean cajaCuotaIngresoBean) throws Exception;

    public List<DocIngresoRepBean> obtenerCuotaIngreso(CuotaIngresoBean cuotaIngresoBean) throws Exception;

    public List<DetDocIngresoRepBean> obtenerFormatoDetalleCuotaIngreso(@Param("idCuotaIngreso") Integer idCuotaIngreso, @Param("uniNeg") String uniNeg) throws Exception;
    
    public List<CuotaIngresoRepBean> obtenerCuotaReporte(@Param("idCajaCuotaIngreso") Integer idCajaCuotaIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public CuotaIngresoBean obtenerIdCuotaIngreso(CuotaIngresoBean cuotaIngresoBean) throws Exception;
    
    public List<CuotaIngresoBean> obtenerIngresosEnCaja(CuotaIngresoBean cuotaIngresoBean) throws Exception;
    
    public List<CuotaIngresoBean> obtenerFiltroDetalleMovimientosCuoIng(CuotaIngresoBean cuotaIngresoBean) throws Exception;
    

}
