/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.ReporteRechazoBean;
import pe.marista.sigma.dao.ReporteRechazoDAO;

/**
 *
 * @author MS002
 */
public class ReporteRechazoService {

    private ReporteRechazoDAO reporteRechazoDAO;

    public List<ReporteRechazoBean> obtenerReporteRechazo(String uniNeg) throws Exception {
        return reporteRechazoDAO.obtenerReporteRechazo(uniNeg);
    }

    public List<ReporteRechazoBean> obtenerReporteRechazoPorId(String uniNeg, Integer idReporteRechazo) throws Exception {
        return reporteRechazoDAO.obtenerReporteRechazoPorId(uniNeg, idReporteRechazo);
    }

    public List<ReporteRechazoBean> obtenerRechazoPorCuenta(String uniNeg, Integer idCtasXCobrar) throws Exception {
        return reporteRechazoDAO.obtenerRechazoPorCuenta(uniNeg, idCtasXCobrar);
    }

    public Integer obtenerCantidadRechazos(String uniNeg, Integer idProcesoBanco) throws Exception {
        return reporteRechazoDAO.obtenerCantidadRechazos(uniNeg, idProcesoBanco);
    }
 
    public Integer obtenerMaxIdRechazo(String uniNeg) throws Exception {
        return reporteRechazoDAO.obtenerMaxIdRechazo(uniNeg);
    }

    public Integer obtenerMinIdRechazo(String uniNeg) throws Exception {
        return reporteRechazoDAO.obtenerMinIdRechazo(uniNeg);
    }
    
    public void insertarReporteRechazo(ReporteRechazoBean reporteRechazoBean) throws Exception {
        reporteRechazoDAO.insertarReporteRechazo(reporteRechazoBean);
    }

    public void modificarStatusError(ReporteRechazoBean reporteRechazoBean) throws Exception {
        reporteRechazoDAO.modificarStatusError(reporteRechazoBean);
    }

    public ReporteRechazoDAO getReporteRechazoDAO() {
        return reporteRechazoDAO;
    }

    public void setReporteRechazoDAO(ReporteRechazoDAO reporteRechazoDAO) {
        this.reporteRechazoDAO = reporteRechazoDAO;
    }
    
}
