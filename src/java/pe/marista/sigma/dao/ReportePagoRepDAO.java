/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.reporte.ReportePagoRepBean;

/**
 *
 * @author JC
 */
public interface ReportePagoRepDAO {
    public List<ReportePagoRepBean> obtenerTotalRepBanco(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;
}
