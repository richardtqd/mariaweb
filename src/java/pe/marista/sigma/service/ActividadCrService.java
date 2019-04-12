/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ActividadCrBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.ActividadCrDAO;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author MS002
 */
public class ActividadCrService {

    private ActividadCrDAO actividadCrDAO;

    public List<ActividadCrBean> obtenerActividadCr(String uniNeg) throws Exception {
        return actividadCrDAO.obtenerActividadCr(uniNeg);
    }

    public List<ActividadCrBean> obtenerGrafoPres(String uniNeg) throws Exception {
        return actividadCrDAO.obtenerGrafoPres(uniNeg);
    }

    public List<ActividadCrBean> obtenerPorCuenta(Integer cuenta) throws Exception {
        return actividadCrDAO.obtenerPorCuenta(cuenta);
    }

    public void insertarActividadCr(ActividadCrBean actividadCrBean, List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) throws Exception {
        System.out.println(">>>>>>>>>" + listaCentroResponsabilidadBean.size());
        System.out.println(actividadCrBean.getIdActividad());
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        List<ActividadCrBean> listaActCr = new ArrayList<>();
        listaActCr = actividadCrDAO.obtenerActividadCrId(actividadCrBean);
        //Eliminando lista
        for (int i = 0; i < listaActCr.size(); i++) {
            actividadCrDAO.eliminarActividadCr(listaActCr.get(i));
            System.out.println("Eliminado");
        }
        //Insertando Lista 
        for (Object cr : listaCentroResponsabilidadBean) {
            actividadCrBean.getCentroResponsabilidadBean().setCr(new Integer(cr.toString()));
            System.out.println(new Integer(cr.toString()));
            System.out.println("Insertar");
            actividadCrDAO.insertarActividadCr(actividadCrBean);
        }
    }

    @Transactional
    public void insertarDetaActividadCr(ActividadCrBean actividadCrBean, List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) throws Exception {
        List<ActividadCrBean> listaActCr = new ArrayList<>();
        listaActCr = actividadCrDAO.obtenerDetActividadCrId(actividadCrBean);
        //Eliminando lista
        for (int i = 0; i < listaActCr.size(); i++) {
            actividadCrDAO.eliminarActCr(listaActCr.get(i));
        }
        //Insertando Lista 
        for (Object cr : listaCentroResponsabilidadBean) {
            actividadCrBean.getCentroResponsabilidadBean().setCr(new Integer(cr.toString()));
            actividadCrDAO.insertarActCr(actividadCrBean);
        }
    }

    @Transactional
    public void insertarDetaActividadCr2(ActividadCrBean actividadCrBean, List<ActividadCrBean> listaCentroResponsabilidadBean) throws Exception {
        List<ActividadCrBean> listaActCr = new ArrayList<>();
        listaActCr = actividadCrDAO.obtenerDetActividadCrId(actividadCrBean);
        //Eliminando lista
        for (int i = 0; i < listaActCr.size(); i++) {
            actividadCrDAO.eliminarActCr(listaActCr.get(i));
        }
        //Insertando Lista 
        for (ActividadCrBean cr : listaCentroResponsabilidadBean) {
            ActividadCrBean acr = new ActividadCrBean();
            acr = cr;
            acr.getCentroResponsabilidadBean().setCr(cr.getCentroResponsabilidadBean().getCr());
            acr.setValorD(cr.getValorD());
            actividadCrDAO.insertarActCr(acr);
        }
    }

    public void modificarActividadCr(ActividadCrBean actividadCrBean, String idCr, Integer idActividad) throws Exception {
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        Integer cr = Integer.parseInt(idCr);
        List<ActividadCrBean> listActividadCrBean = new ArrayList<>();
        listActividadCrBean = actividadCrDAO.obtenerSubCr(idActividad, cr, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        for (ActividadCrBean actCr : listActividadCrBean) {
            actCr.getPlanContableBean().setCuenta(actividadCrBean.getPlanContableBean().getCuenta());
            System.out.println("cuenta=>>>>" + actCr.getPlanContableBean().getCuenta());
            actividadCrDAO.modificarCuentaSub(actCr);
        }
//        actividadCrDAO.modificarActividadCr(actividadCrBean);
    }

    @Transactional
    public void insertarActividadCrCuenta(List<ActividadCrBean> listaCrBean, Integer cuenta) throws Exception {
        ActividadCrBean actividadCr = new ActividadCrBean();
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        for (ActividadCrBean cr : listaCrBean) {
            actividadCr.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            actividadCr.getUnidadOrganicaBean().setIdUniOrg(cr.getUnidadOrganicaBean().getIdUniOrg());
            actividadCr.setAnio(cr.getAnio());
            actividadCr.getPlanContableBean().setCuenta(cuenta);
            actividadCr.setIdObjOperativo(cr.getIdObjOperativo());
            actividadCr.setIdActividad(cr.getIdActividad());
            actividadCr.getCentroResponsabilidadBean().setCr(cr.getCentroResponsabilidadBean().getCr());
            actividadCr.setCreaPor(beanUsuarioSesion.getUsuario());
            actividadCrDAO.insertarActividadCrCuenta(actividadCr);
            break;
        }
    }

//    public void insertarActividadCrCuenta(ActividadCrBean actividadCrBean) throws Exception {
//        actividadCrDAO.insertarActividadCrCuenta(actividadCrBean);
//    } 
    @Transactional
    public void eliminarActividadCrXDetAct(List<DetActividadBean> listaDeta) throws Exception {
        Integer cuenta = 0;
        for (int i = 0; i < listaDeta.size(); i++) {
            cuenta = listaDeta.get(i).getPlanContableBean().getCuenta();
        }
        System.out.println(">>>>>" + cuenta);
        List<ActividadCrBean> listaActividadCrBean = new ArrayList<>();
        listaActividadCrBean = actividadCrDAO.obtenerPorCuenta(cuenta);
        for (ActividadCrBean actCr : listaActividadCrBean) {
            System.out.println(actCr.getAnio());
            System.out.println(actCr.getIdObjOperativo());
            System.out.println(actCr.getPlanContableBean().getCuenta());
            System.out.println(actCr.getObjOperativoBean().getIdObjOperativo());
            System.out.println(actCr.getUnidadNegocioBean().getUniNeg());
            System.out.println(actCr.getUnidadOrganicaBean().getIdUniOrg());
            actividadCrDAO.eliminarActividadCrCuenta(actCr);
            break;
        }
    }

    public List<ActividadCrBean> obtenerPresupuestoCr(String uniNeg) throws Exception {
        List<ActividadCrBean> lista = actividadCrDAO.obtenerPresupuestoCr(uniNeg);
        return actividadCrDAO.obtenerPresupuestoCr(uniNeg);
    }

    public List<ActividadCrBean> obtenerPresupuestoCrId(String uniNeg, Integer cr) throws Exception {
        return actividadCrDAO.obtenerPresupuestoCrId(uniNeg, cr);
    }

    public void modificarCuentaSub(ActividadCrBean actividadCrBean) throws Exception {
        actividadCrDAO.modificarCuentaSub(actividadCrBean);
    }

    public Integer obetenerPresupuestoGeneralExec(Integer cr, String uniNeg, Integer cuenta) throws Exception {
        return actividadCrDAO.obetenerPresupuestoGeneralExec(cr, uniNeg, cuenta);
    }

    public void eliminarActividadCr(ActividadCrBean actividadCrBean) throws Exception {
        actividadCrDAO.eliminarActividadCr(actividadCrBean);
    }

    public void eliminarActividadCrCuenta(ActividadCrBean actividadCrBean) throws Exception {
        actividadCrDAO.eliminarActividadCrCuenta(actividadCrBean);
    }

    public ActividadCrBean obtenerPorId(ActividadCrBean actividadCrBean) throws Exception {
        return actividadCrDAO.obtenerPorId(actividadCrBean);
    }

    public List<ActividadCrBean> obtenerActividadCrId(ActividadCrBean actividadCrBean) throws Exception {
        return actividadCrDAO.obtenerActividadCrId(actividadCrBean);
    }

    public List<ActividadCrBean> obtenerDibujoPresupuestoCr(Integer cr, String uniNeg) throws Exception {
        return actividadCrDAO.obtenerDibujoPresupuestoCr(cr, uniNeg);
    }

    public List<ActividadCrBean> obtenerCrPorActividad(Integer cuenta) throws Exception {
        return actividadCrDAO.obtenerCrPorActividad(cuenta);
    }

    public List<ActividadCrBean> obtenerSubCr(Integer idActividad, Integer cr, String uniNeg) throws Exception {
        return actividadCrDAO.obtenerSubCr(idActividad, cr, uniNeg);
    }

    public List<ActividadCrBean> obtenerSubCrCuenta(Integer idActividad, Integer cr, String uniNeg, Integer cuenta) throws Exception {
        return actividadCrDAO.obtenerSubCrCuenta(idActividad, cr, uniNeg, cuenta);
    }

    @Transactional
    public void insertarActCr(ActividadCrBean actividadCrBean) throws Exception {
        actividadCrDAO.insertarActCr(actividadCrBean);
    }

    public List<ActividadCrBean> obtenerDetActividadCrId(ActividadCrBean actividadCrBean) throws Exception {
        return actividadCrDAO.obtenerDetActividadCrId(actividadCrBean);
    }

    public ActividadCrDAO getActividadCrDAO() {
        return actividadCrDAO;
    }

    public void setActividadCrDAO(ActividadCrDAO actividadCrDAO) {
        this.actividadCrDAO = actividadCrDAO;
    }

}
