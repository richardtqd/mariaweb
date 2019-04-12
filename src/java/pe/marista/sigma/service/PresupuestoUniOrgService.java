/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.PresupuestoUniOrgBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetPresUniOrgRepBean;
import pe.marista.sigma.bean.reporte.PresUniOrgRepBean;
import pe.marista.sigma.dao.PresupuestoUniOrgDAO;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author MS002
 */
public class PresupuestoUniOrgService {

    private PresupuestoUniOrgDAO presupuestoUniOrgDAO;

    public List<PresupuestoUniOrgBean> obtenerPresupuestoUniOrg(String uniNeg) throws Exception {
        return presupuestoUniOrgDAO.obtenerPresupuestoUniOrg(uniNeg);
    }

    public List<PresupuestoUniOrgBean> obtenerPorPlanOperativo(Integer idUniOrg, String uniNeg) throws Exception {
        return presupuestoUniOrgDAO.obtenerPorPlanOperativo(idUniOrg, uniNeg);
    }

    public List<PresupuestoUniOrgBean> obtenerPorCuenta(Integer cuenta, String uniNeg) throws Exception {
        return presupuestoUniOrgDAO.obtenerPorCuenta(cuenta, uniNeg);
    }

    @Transactional
    public void insertarPresupuestoUniOrg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception {
        presupuestoUniOrgDAO.insertarPresupuestoUniOrg(presupuestoUniOrgBean);
    }

    public void insertarPresupuestoUo(List<DetActividadBean> listaDetActividadBean, Integer cuenta, Integer anio, Integer idUniOrg) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        //Obteniendo Pres-UO
        List<PresupuestoUniOrgBean> listaPresupuestoUniOrgBean = new ArrayList<>();
        PresupuestoUniOrgBean presupuestoUniOrgBean = new PresupuestoUniOrgBean();
        PresupuestoUniOrgBean presUo = new PresupuestoUniOrgBean();
    }

    @Transactional
    public void modificarPresupuestoUniOrg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception {
        presupuestoUniOrgDAO.modificarPresupuestoUniOrg(presupuestoUniOrgBean);
    }

    @Transactional
    public void eliminarPresupuestoUniOrg(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception {
        presupuestoUniOrgDAO.eliminarPresupuestoUniOrg(presupuestoUniOrgBean);
    }

    public PresupuestoUniOrgBean obtenerPorId(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception {
        return presupuestoUniOrgDAO.obtenerPorId(presupuestoUniOrgBean);
    }

    public List<PresupuestoUniOrgBean> obtenerListaPorId(PresupuestoUniOrgBean presupuestoUniOrgBean) throws Exception {
        return presupuestoUniOrgDAO.obtenerListaPorId(presupuestoUniOrgBean);
    }

    public List<PresupuestoUniOrgBean> obtenerPresupuestoOrg(String uniNeg) throws Exception {
        return presupuestoUniOrgDAO.obtenerPresupuestoOrg(uniNeg);
    }

    public List<PresupuestoUniOrgBean> obtenerPresupuestoOrgId(String uniNeg, Integer anio) throws Exception {
        return presupuestoUniOrgDAO.obtenerPresupuestoOrgId(uniNeg, anio);
    }

    @Transactional
    public Object execProPresUniOrg(String uniNeg) throws Exception {
        return presupuestoUniOrgDAO.execProPresUniOrg(uniNeg);
    }

    public PresupuestoUniOrgDAO getPresupuestoUniOrgDAO() {
        return presupuestoUniOrgDAO;
    }

    public void setPresupuestoUniOrgDAO(PresupuestoUniOrgDAO presupuestoUniOrgDAO) {
        this.presupuestoUniOrgDAO = presupuestoUniOrgDAO;
    }

    public List<PresUniOrgRepBean> obtenerPresupuestoPorUniOrgForTop1(String uniNeg, Integer anio, List<Integer> ids) throws Exception {
        return presupuestoUniOrgDAO.obtenerPresupuestoPorUniOrgForTop1(uniNeg, anio, ids);
    }

    public List<PresUniOrgRepBean> obtenerPresupuestoPorUniOrgFor(String uniNeg, Integer anio, List<Integer> ids) throws Exception {
        return presupuestoUniOrgDAO.obtenerPresupuestoPorUniOrgFor(uniNeg, anio, ids);
    }

    public List<DetPresUniOrgRepBean> obtenerDetPresupuestoPorUniOrg(String uniNeg, Integer anio, Integer idUniOrg) throws Exception {
        return presupuestoUniOrgDAO.obtenerDetPresupuestoPorUniOrg(uniNeg, anio, idUniOrg);
    }

    public List<PresupuestoUniOrgBean> filtrarPresupuesto(PresupuestoBean presupuestoBean) throws Exception {
        return presupuestoUniOrgDAO.filtrarPresupuesto(presupuestoBean);
    }

}
