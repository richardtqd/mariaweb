/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetPresupuestoNewBean;
import pe.marista.sigma.bean.PresupuestoNewBean;
import pe.marista.sigma.bean.reporte.PrespuestoCabeceraRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCentroRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCuentaRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoDetalleRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoNewInicioRepBean;
import pe.marista.sigma.dao.PresupuestoNewDAO;

/**
 *
 * @author Administrador
 */
public class PresupuestoNewService {

    private PresupuestoNewDAO presupuestoNewDAO;

    @Transactional
    public void insertarPresupuestoCrCuentas(PresupuestoNewBean presupuestoBean) throws Exception {
        presupuestoNewDAO.insertarPresupuestoCrCuentas(presupuestoBean);
    }

    @Transactional
    public void modificarPresupuestoCrCuentas(PresupuestoNewBean presupuestoBean) throws Exception {
        presupuestoNewDAO.modificarPresupuestoCrCuentas(presupuestoBean);
    }

    public PresupuestoNewBean obtenerPresupuesto(PresupuestoNewBean presupuestoBean) throws Exception {
        return presupuestoNewDAO.obtenerPresupuesto(presupuestoBean);
    }

    public List<PresupuestoNewBean> obtenerListaPresupuesto(PresupuestoNewBean presupuestoBean) throws Exception {
        return presupuestoNewDAO.obtenerListaPresupuesto(presupuestoBean);
    }

    public void eliminarPresupuestoCrCuentas(PresupuestoNewBean presupuestoBean) throws Exception {
        presupuestoNewDAO.eliminarPresupuestoCrCuentas(presupuestoBean);
    }

    //Reportes Presupuesto
    public List<PrespuestoCabeceraRepBean> obtenerCabecera(String uniNeg, Integer anio, String tipoCuenta) throws Exception {
        return presupuestoNewDAO.obtenerCabecera(uniNeg, anio, tipoCuenta);
    }

    public List<PresupuestoCuentaRepBean> obtenerCuentas(String uniNeg, Integer anio, String tipoCuenta, String inicio) throws Exception {
        return presupuestoNewDAO.obtenerCuentas(uniNeg, anio, tipoCuenta, inicio);
    }

    public List<PresupuestoCentroRepBean> obtenerCentroResp(String uniNeg, Integer anio, String tipoCuenta, Integer cuenta) throws Exception {
        return presupuestoNewDAO.obtenerCentroResp(uniNeg, anio, tipoCuenta, cuenta);
    }

    public List<PresupuestoNewBean> obtenerListaPresupuestoVista(PresupuestoNewBean presupuestoBean) throws Exception {
        return presupuestoNewDAO.obtenerListaPresupuestoVista(presupuestoBean);

    }
    //Reporte Por Centro

    public List<PresupuestoCuentaRepBean> obtenerCuentasResp(String uniNeg, Integer anio, String tipoCuenta, Integer cr) throws Exception {
        return presupuestoNewDAO.obtenerCuentasResp(uniNeg, anio, tipoCuenta, cr);
    }

    public List<PresupuestoCentroRepBean> obtenerCentroRespResp(String uniNeg, Integer anio, String tipoCuenta, String inicio) throws Exception {
        return presupuestoNewDAO.obtenerCentroRespResp(uniNeg, anio, tipoCuenta, inicio);
    }

    public List<PresupuestoNewBean> obtenerPresupuestoPorCRAnio(String uniNeg, Integer anio) throws Exception {
        return presupuestoNewDAO.obtenerPresupuestoPorCRAnio(uniNeg, anio);
    }

    public PresupuestoNewBean SP_obtenerPresupuestoPorId(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin) throws Exception {
        return presupuestoNewDAO.SP_obtenerPresupuestoPorId(presupuesto, fechaIni, fechaFin);
    }

    public List<PresupuestoNewBean> SP_obtenerPresupuestoPorCRAnio(String uniNeg, Integer anio, Date fechaIni, Date fechaFin, List<Integer> cr, List<Integer> cc, Integer flg, Integer flg2, Integer flg3,Integer verpor) throws Exception {
        return presupuestoNewDAO.SP_obtenerPresupuestoPorCRAnio(uniNeg, anio, fechaIni, fechaFin, cr, cc, flg, flg2, flg3,verpor);
    }

    public List<DetPresupuestoNewBean> SP_obtenerDetalleGastosCrCuenta(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin) throws Exception {
        return presupuestoNewDAO.SP_obtenerDetalleGastosCrCuenta(presupuesto, fechaIni, fechaFin);
    }

    public List<DetPresupuestoNewBean> SP_obtenerDetalleGastosCrCuentaList(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin, List<Integer> cr, List<Integer> cc, Integer flg, Integer flg2, Integer flg3,Integer verpor) throws Exception {
        return presupuestoNewDAO.SP_obtenerDetalleGastosCrCuentaList(presupuesto, fechaIni, fechaFin, cr, cc, flg, flg2, flg3,verpor);
    }

    //getter and stter
    public PresupuestoNewDAO getPresupuestoNewDAO() {
        return presupuestoNewDAO;
    }

    public void setPresupuestoNewDAO(PresupuestoNewDAO presupuestoNewDAO) {
        this.presupuestoNewDAO = presupuestoNewDAO;
    }

    public List<PresupuestoNewInicioRepBean> obtenerInicioCentroRespResp(String uniNeg, Integer anio, String tipoCuenta) throws Exception {
        return presupuestoNewDAO.obtenerInicioCentroRespResp(uniNeg, anio, tipoCuenta);
    }

    public List<PresupuestoNewInicioRepBean> obtenerInicioCuentas(String uniNeg, Integer anio, String tipoCuenta) throws Exception {
        return presupuestoNewDAO.obtenerInicioCuentas(uniNeg, anio, tipoCuenta);
    }

    public List<PresupuestoDetalleRepBean> obtenerTituloDetalle(String uniNeg, Integer anio) throws Exception {
        return presupuestoNewDAO.obtenerTituloDetalle(uniNeg, anio);
    }

    public List<PresupuestoDetalleRepBean> obtenerEjecutado(String uniNeg, Integer anio, Integer cuenta) throws Exception {
        return presupuestoNewDAO.obtenerEjecutado(uniNeg, anio, cuenta);
    }

    public List<PresupuestoDetalleRepBean> obtenerPorcentaje(String uniNeg, Integer anio, Integer cuenta) throws Exception {
        return presupuestoNewDAO.obtenerPorcentaje(uniNeg, anio, cuenta);
    }

    public List<PresupuestoDetalleRepBean> obtenerUtilizado(String uniNeg, Integer anio, Integer cuenta) throws Exception {
        return presupuestoNewDAO.obtenerUtilizado(uniNeg, anio, cuenta);
    }

    public List<PresupuestoDetalleRepBean> obtenerDisponible(String uniNeg, Integer anio, Integer cuenta) throws Exception {
        return presupuestoNewDAO.obtenerDisponible(uniNeg, anio, cuenta);
    }

    public List<DetPresupuestoNewBean> SP_obtenerConsolidado(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin) throws Exception {
        return presupuestoNewDAO.SP_obtenerConsolidado(presupuesto, fechaIni, fechaFin);
    }

    public List<PrespuestoCabeceraRepBean> obtenerCabeceraIngresos(String uniNeg, Integer anio, String tipoCuenta) throws Exception {
        return presupuestoNewDAO.obtenerCabeceraIngresos(uniNeg, anio, tipoCuenta);
    }

    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuesto(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin, List<Integer> cc) throws Exception {
        return presupuestoNewDAO.SP_obtenerIngresosPresupuesto(presupuesto, fechaIni, fechaFin, cc);
    }

    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuestoDetallado(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin, List<Integer> cc) throws Exception {
        return presupuestoNewDAO.SP_obtenerIngresosPresupuestoDetallado(presupuesto, fechaIni, fechaFin, cc);
    }

    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuestoCabecera(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin, List<Integer> cc) throws Exception {
        return presupuestoNewDAO.SP_obtenerIngresosPresupuestoCabecera(presupuesto, fechaIni, fechaFin, cc);
    }

    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuestoMesaMes(PresupuestoNewBean presupuesto, Date fechaIni, Date fechaFin, List<Integer> cc) throws Exception {
        return presupuestoNewDAO.SP_obtenerIngresosPresupuestoMesaMes(presupuesto, fechaIni, fechaFin, cc);
    }

}
