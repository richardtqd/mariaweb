/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.NivelTipoAccesoBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.dao.NivelTipoAccesoDAO;

/**
 *
 * @author MS-001
 */
public class NivelTipoAccesoService {

    private NivelTipoAccesoDAO nivelTipoAccesoDAO;

    @Transactional
    public void insertarNivelTipoAccesoDual(NivelTipoAccesoBean nivelTipoAccesoBean, List<CentroResponsabilidadBean> dualCR, List<PlanContableBean> dualCuenta) throws Exception {
        //nivel CR
        NivelTipoAccesoBean eliminarAcceso = new NivelTipoAccesoBean();
        eliminarAcceso=nivelTipoAccesoBean;
        nivelTipoAccesoDAO.eliminarNivelTipoAcceso(eliminarAcceso);
        for (Object listaCR : dualCR) {
            NivelTipoAccesoBean acceso = new NivelTipoAccesoBean();
            String cr = "";
            cr = listaCR.toString();
            acceso = nivelTipoAccesoBean;
            acceso.setNivel(new Integer(cr));
            acceso.setTipoNivel("CR"); 
//            acceso.setStatus(Boolean.TRUE);
            nivelTipoAccesoDAO.insertarNivelTipoAcceso(acceso);
        }
        for (Object listaCta : dualCuenta) {
            NivelTipoAccesoBean acceso = new NivelTipoAccesoBean();
            String cta = "";
            cta = listaCta.toString();
            acceso = nivelTipoAccesoBean;
            acceso.setNivel(new Integer(cta));
            acceso.setTipoNivel("CC");
//            acceso.setStatus(Boolean.TRUE);
            nivelTipoAccesoDAO.insertarNivelTipoAcceso(acceso);
        }
    }

    public void insertarNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception {
        nivelTipoAccesoDAO.insertarNivelTipoAcceso(niveTipoAccesoBean);
    }

    public void actualizarNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception {
        nivelTipoAccesoDAO.actualizarNivelTipoAcceso(niveTipoAccesoBean);
    }

    public void eliminarNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception {
        nivelTipoAccesoDAO.eliminarNivelTipoAcceso(niveTipoAccesoBean);
    }
 
    public List<NivelTipoAccesoBean> obtenerNivelTipoAccesoPorAnio(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception {
        return nivelTipoAccesoDAO.obtenerNivelTipoAccesoPorAnio(niveTipoAccesoBean);
    }

    public NivelTipoAccesoBean obtenerNivelTipoAcceso(NivelTipoAccesoBean niveTipoAccesoBean) throws Exception {
        return nivelTipoAccesoDAO.obtenerNivelTipoAcceso(niveTipoAccesoBean);
    }

    ////////
    public NivelTipoAccesoDAO getNivelTipoAccesoDAO() {
        return nivelTipoAccesoDAO;
    }

    public void setNivelTipoAccesoDAO(NivelTipoAccesoDAO nivelTipoAccesoDAO) {
        this.nivelTipoAccesoDAO = nivelTipoAccesoDAO;
    }

}
