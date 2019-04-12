/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.reporte.CronogramaPagoRepBean;
import pe.marista.sigma.dao.CronogramaPagoDAO;

/**
 *
 * @author MS002
 */
public class CronogramaPagoService {

    private CronogramaPagoDAO cronogramaPagoDAO;

    public List<CronogramaPagoBean> obtenerCronogramaPago(String uniNeg, Integer anio) throws Exception {
        return cronogramaPagoDAO.obtenerCronogramaPago(uniNeg, anio);
    }

    public void insertarCronogramaPago(CronogramaPagoBean cronogramaPagoBean) throws Exception {
        cronogramaPagoDAO.insertarCronogramaPago(cronogramaPagoBean);
    }

    public void actualizarCronogramaPago(CronogramaPagoBean cronogramaPagoBean) throws Exception {
        cronogramaPagoDAO.actualizarCronogramaPago(cronogramaPagoBean);
    }

    public CronogramaPagoBean obtenerIdCronograma(String uniNeg, Integer idCronogramaPago) throws Exception {
        return cronogramaPagoDAO.obtenerIdCronograma(uniNeg, idCronogramaPago);
    }

    public CronogramaPagoBean validarCronograma(String uniNeg, Integer anio, Integer mes, String fecha) throws Exception {
        return cronogramaPagoDAO.validarCronograma(uniNeg, anio, mes, fecha);
    }

    public List<CronogramaPagoBean> obtenerCronogramaAnio(String uniNeg, Integer anio) throws Exception {
        return cronogramaPagoDAO.obtenerCronogramaAnio(uniNeg, anio);
    }

    public List<CronogramaPagoRepBean> obtenerCronogramaAnioRep(String uniNeg, Integer anio) throws Exception {
        return cronogramaPagoDAO.obtenerCronogramaAnioRep(uniNeg, anio);
    }

    public CronogramaPagoDAO getCronogramaPagoDAO() {
        return cronogramaPagoDAO;
    }

    public void setCronogramaPagoDAO(CronogramaPagoDAO cronogramaPagoDAO) {
        this.cronogramaPagoDAO = cronogramaPagoDAO;
    }

}
